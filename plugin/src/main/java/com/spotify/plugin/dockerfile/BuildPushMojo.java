/*-
 * -\-\-
 * Dockerfile Maven Plugin
 * --
 * Copyright (C) 2016 Spotify AB
 * --
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * -/-/-
 */

package com.spotify.plugin.dockerfile;

import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerException;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import static com.spotify.plugin.dockerfile.TagsSelector.select;

@Mojo(name = "buildPush",
        defaultPhase = LifecyclePhase.DEPLOY,
        requiresProject = true,
        threadSafe = true)
public class BuildPushMojo extends AbstractDockerMojo {

    /**
     * Directory containing the the build context. This is typically the directory that contains
     * your Dockerfile.
     */
    @Parameter(defaultValue = "${project.basedir}",
            property = "dockerfile.contextDirectory",
            required = true)
    private File contextDirectory;

    /**
     * Path to the Dockerfile to build. The specified file must reside withing the build context
     */
    @Parameter(property = "dockerfile.dockerfile", required = false)
    private File dockerfile;

    /**
     * The repository to put the built image into, for example <tt>spotify/foo</tt>.  You should also
     * set the <tt>tag</tt> parameter, otherwise the tag <tt>latest</tt> is used by default.
     */
    @Parameter(property = "dockerfile.repository")
    private String repository;

    /**
     * The tag to apply to the built image.
     */
    @Parameter(property = "dockerfile.tag")
    private String tag;

    @Parameter(property = "dockerfile.tags")
    private List<String> tags;

    /**
     * Disables the push goal; it becomes a no-op.
     */
    @Parameter(property = "dockerfile.buildPush.skip", defaultValue = "false")
    private boolean skipBuildPush;

    /**
     * Updates base images automatically.
     */
    @Parameter(property = "dockerfile.build.pullNewerImage", defaultValue = "false")
    private boolean pullNewerImage;

    /**
     * Do not use cache when building the image.
     */
    @Parameter(property = "dockerfile.build.noCache", defaultValue = "false")
    private boolean noCache;

    /**
     * Custom build arguments.
     */
    @Parameter(property = "dockerfile.buildArgs")
    private Map<String, String> buildArgs;

    @Parameter(property = "dockerfile.build.cacheFrom")
    private List<String> cacheFrom;

    @Parameter(property = "dockerfile.build.squash", defaultValue = "false")
    private boolean squash;

    /**
     * Allow failure.
     */
    @Parameter(property = "dockerfile.build.failure.ignore", defaultValue = "false")
    private boolean buildFailureIgnore;

    /**
     * Whether to force re-assignment of an already assigned tag.
     */
    @Parameter(property = "dockerfile.force", defaultValue = "true", required = true)
    private boolean force;

    /**
     * Allow failure.
     */
    @Parameter(property = "dockerfile.buildPush.failure.ignore", defaultValue = "false")
    private boolean buildPushFailureIgnore;

    @Override
    protected void execute(DockerClient dockerClient)
            throws MojoExecutionException, MojoFailureException {
        if (buildPushFailureIgnore) {
            try {
                executeInternal(dockerClient);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            executeInternal(dockerClient);
        }
    }

    protected void executeInternal(DockerClient dockerClient)
            throws MojoExecutionException, MojoFailureException {
        final Log log = getLog();

        if (skipBuildPush) {
            log.info("Skipping execution because 'dockerfile.buildPush.skip' is set");
            return;
        }

        if (repository == null) {
            repository = readMetadata(Metadata.REPOSITORY);
        }

        List<String> tagsToBuildPush = select(tags, tag, readMetadata(Metadata.TAG));

        String tagToBuild = tagsToBuildPush.get(0);

        log.info("dockerfile: " + dockerfile);
        log.info("contextDirectory: " + contextDirectory);

        Path dockerfilePath = null;
        if (dockerfile != null) {
            dockerfilePath = dockerfile.toPath();
        }
        final String imageId = BuildMojo.buildImage(
                dockerClient, log, verbose, contextDirectory.toPath(), dockerfilePath, repository, tagToBuild,
                pullNewerImage, noCache, buildArgs, cacheFrom, squash);

        if (imageId == null) {
            log.warn("Docker build was successful, but no image was built");
        } else {
            log.info(MessageFormat.format("Detected build of image with id {0}", imageId));
            writeMetadata(Metadata.IMAGE_ID, imageId);
        }

        // Do this after the build so that other goals don't use the tag if it doesn't exist
        if (repository != null) {
            writeImageInfo(repository, tagToBuild);
        }

        writeMetadata(log);

        if (repository == null) {
            log.info(MessageFormat.format("Successfully built {0}", imageId));
        } else {
            log.info(MessageFormat.format("Successfully built {0}", formatImageName(repository, tagToBuild)));
        }

        // If more tags are given, apply to image.
        for (int i = 0; i < tagsToBuildPush.size(); i++) {
            String currentTag = tagsToBuildPush.get(i);
            try {
                if (i > 0) {
                    String repoTag = repository + ':' + currentTag;
                    dockerClient.tag(imageId, repoTag, force);
                    log.info(MessageFormat.format("Successfully tagged image {0} as {1}", imageId, repoTag));
                }
                dockerClient
                        .push(formatImageName(repository, currentTag), LoggingProgressHandler.forLog(log, verbose));
            } catch (DockerException | InterruptedException e) {
                throw new MojoExecutionException("Could not tag / push image", e);
            }
        }

    }
}
