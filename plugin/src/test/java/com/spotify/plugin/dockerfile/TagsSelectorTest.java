/*-
 * -\-\-
 * Dockerfile Maven Plugin
 * --
 * Copyright (C) 2016 - 2020 Spotify AB
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

import static com.spotify.plugin.dockerfile.AbstractDockerMojo.LATEST;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.google.common.collect.ImmutableList;
import java.util.List;
import org.junit.Test;

public class TagsSelectorTest {

  @Test
  public void validateTagsAreSelectedCorrectly() {

    TagsSelector selector = new TagsSelector();

    // Build
    //
    // <tags>
    //   <tag>latest</tag>
    // </tags>
    //
    assertThat(selector.select(ImmutableList.of(LATEST), null, null)).asList().containsExactly(LATEST);

    // Build
    //
    // <tags>
    //   <tag>latest</tag>
    //   <tag>332-e.1</tag>
    // </tags>
    //
    assertThat(selector.select(ImmutableList.of(LATEST, "332-e.1"), null, null)).asList().containsExactly(LATEST, "332-e.1");

    // Build
    //
    // <tag>1.0</tag>
    //
    assertThat(selector.select(ImmutableList.of(), "1.0", null)).asList().containsExactly("1.0");
    assertThat(selector.select(null, "1.0", null)).asList().containsExactly("1.0");

    // Build
    //
    assertThat(selector.select(null, null, null)).asList().containsExactly(LATEST);

    // Push
    //
    // metadata tag = 5.0
    //
    assertThat(selector.select(null, null, "5.0")).asList().containsExactly("5.0");
  }
}
