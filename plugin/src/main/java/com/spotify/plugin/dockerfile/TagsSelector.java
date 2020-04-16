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

import com.google.common.collect.Lists;
import java.util.List;

public class TagsSelector {

  public static List<String> select(List<String> tags, String tag, String tagFromMetadata) {
    List<String> tagsToUse = Lists.newArrayList();
    if (tags != null && !tags.isEmpty()) {
      //
      // If we have a tags list then we will use those values
      //
      // <tags>
      //   <tag>${project.version}</tag>
      //   <tag>latest</tag>
      // </tags>
      //
      tagsToUse.addAll(tags);
    } else {
      //
      // Otherwise:
      //
      // 1) take the tag if it is set
      // 2) use tag as specified in the metadata if present
      // 3) use latest
      //
      if (tag != null) {
        tagsToUse.add(tag);
      } else {
        // Metadata is written by the BuildMojo and this will only be picked up by the PushMojo
        if (tagFromMetadata != null) {
          tag = tagFromMetadata;
        } else {
          tag = "latest";
        }
        tagsToUse.add(tag);
      }
    }
    return tagsToUse;
  }
}
