# Usage

## Maven Goals

Goals available for this plugin:

| Goal | Description    | Default Phase |
| ---- | -------------- | ------------- |
| `dockerfile:build` | Builds a Docker image from a Dockerfile. | package |
| `dockerfile:tag` | Tags a Docker image. | package |
| `dockerfile:push` | Pushes a Docker image to a repository. | deploy |
| `dockerfile:remove` | Removes a Docker image from a repository. | none |

### Skip Docker Goals

You can pass options to maven to disable the docker goals.

| Maven Option  | What Does it Do?           | Default Value |
| ------------- | -------------------------- | ------------- |
| `dockerfile.skip` | Disables the entire dockerfile plugin; all goals become no-ops. | false |
| `dockerfile.build.skip` | Disables the build goal; it becomes a no-op. | false |
| `dockerfile.push.skip` | Disables the push goal; it becomes a no-op. | false |
| `dockerfile.remove.skip` | Disables the remove goal; it becomes a no-op. | false |
| `dockerfile.tag.skip` | Disables the tag goal; it becomes a no-op. | false |

For example, to skip the entire dockerfile plugin:
```
mvn clean package -Ddockerfile.skip
```

### Ignore failures in Docker Goals

You can pass options to maven to disable the docker goals.

| Maven Option  | What Does it Do?           | Default Value |
| ------------- | -------------------------- | ------------- |
| `dockerfile.build.failure.ignore` | Do not fail build when building image fails | false |
| `dockerfile.push.failure.ignore` | Do not fail build when pushing image fails | false |
| `dockerfile.remove.failure.ignore` | Do not fail build when removing image fails | false |
| `dockerfile.tag.failure.ignore` | Do not fail build when tagging image fails | false |

For example, to ignore failures when building an image:
```
mvn clean package -Ddockerfile.build.failure.ignore
```


## Configuration

### General options

| Maven Option  | What Does it Do?           | Required | Default Value |
| ------------- | -------------------------- | -------- | ------------- |
| `dockerfile.googleContainerRegistryEnabled` | Enables Google Container Registry authentication support. | no | false |
| `dockerfile.password` | Password for connecting to the Docker repository | no | none |
| `dockerfile.retryCount` | Certain Docker operations can fail due to mysterious Docker daemon conditions. Sometimes it might be worth it to just retry operations until they succeed.  This parameter controls how many times operations should be retried before they fail. By default, an extra attempt (so up to two attempts) is made before failing. | no | 1 |
| `dockerfile.username` | Username for connecting to the Docker repository | no | none |
| `dockerfile.useProxy` | Allows connecting to Docker Daemon using HTTP proxy if set | no | false |
| `dockerfile.verbose` | Output a verbose log when performing various operations. | no | false |


### Build Phase

| Maven Option  | What Does it Do?           | Required | Default Value |
| ------------- | -------------------------- | -------- | ------------- |
| `dockerfile.contextDirectory` | Directory containing the Dockerfile to build. | yes | none |
| `dockerfile.repository` | The repository to name the built image | no | none |
| `dockerfile.tag` | The tag to apply when building the Dockerfile, which is appended to the repository. | no | latest |
| `dockerfile.tags` | The tags to apply when building the Dockerfile, which is appended to the repository. If `dockerfile.tags` is used then `dockerfile.tag` will be ignored. The first tag is used if and when writing a docker-info jar. | no | latest |
| `dockerfile.build.pullNewerImage` | Updates base images automatically. | no | true |
| `dockerfile.build.noCache` | Do not use cache when building the image. | no | false |
| `dockerfile.build.cacheFrom` | Docker image used as cache-from. Pulled in advance if not exist locally or `pullNewerImage` is `false` | no | none |
| `dockerfile.buildArgs` | Custom build arguments. | no | none |
| `dockerfile.build.squash` | Squash newly built layers into a single new layer (experimental API 1.25+). | no | false |
| `dockerfile.force` | Force the tagging even if the tag is already assigned to another image. | no | true |
| `dockerfile.skipDockerInfo` | Skip creating a Docker info JAR artifact that could be used to depend on an image created by this plugin in another project/module. | no 
| `dockerfile.classifier` | The classifier to apply to the created Docker info JAR. | no | docker-info |
| `dockerfile.finalName` | The name of the generated Docker info JAR. | no | `${project.build.finalName}` |
| `dockerfile.forceCreation` | Require the jar plugin to build a new Docker info JAR even if none of the contents appear to have changed. By default, this plugin looks to see if the output jar exists and inputs have not changed. If these conditions are true, the plugin skips creation of the jar. This does not work when other plugins, like the maven-shade-plugin, are configured to post-process the jar. This plugin can not detect the post-processing, and so leaves the post-processed jar in place. This can lead to failures when those plugins do not expect to find their own output as an input. Set this parameter to `true` to avoid these problems by forcing this plugin to recreate the jar every time. | no | false |


### Tag Phase

| Maven Option  | What Does it Do?           | Required | Default Value |
| ------------- | -------------------------- | -------- | ------------- |
| `dockerfile.force` | Force the tagging even if the tag is already assigned to another image. | no | true |
| `dockerfile.repository` | The repository to name the built image | no | none |
| `dockerfile.tag` | The tag to apply when building the Dockerfile, which is appended to the repository. | no | latest |
