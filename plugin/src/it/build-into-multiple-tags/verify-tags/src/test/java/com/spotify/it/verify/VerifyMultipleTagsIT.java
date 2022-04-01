package com.spotify.it.verify;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient.ListImagesParam;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.Image;

import org.junit.Before;
import org.junit.Test;

public class VerifyMultipleTagsIT {
  
  private DefaultDockerClient dockerCLient;
  private static final String IMAGE_NAME = "test/build-into-multiple-tags";
  
  @Before
  public void setup() throws Exception {
    final DefaultDockerClient.Builder builder = DefaultDockerClient.fromEnv();
    builder.readTimeoutMillis(5000);
    dockerCLient = builder.build();
  }
  
  @Test
  public void testVerifyTags() throws DockerException, InterruptedException {
    // final List<Image> imagesByName = dockerCLient.listImages(ListImagesParam.create("reference", IMAGE_NAME + "*:*") );
    final List<String> tags;
    final List<Image> images = dockerCLient.listImages(ListImagesParam.filter("reference", IMAGE_NAME + ":*") );
    assertThat(images.size(), equalTo(1));

    tags = images.get(0).repoTags();
    assertThat(tags.size(), equalTo(3));
    assertTrue(tags.containsAll(Arrays.asList(IMAGE_NAME + ":1.2.3-SNAPSHOT", IMAGE_NAME + ":latest", IMAGE_NAME + ":third")));
  }
  
}
