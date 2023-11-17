# Changelog

## v1.4.25 (July 17 2022)
- Updated dependencies including docker-client

## v1.4.24 (January 20 2022)
- Updated dependencies including docker-client

## v1.4.23 (January 20 2022)
- Removed plugin goals: exec & rmi
- Do not shade docker-client dependency
- Updated many dependencies and plugin versions

## v1.4.22 (January 19 2022)
- Add failure ignore settings

## v1.4.21 (January 18 2022)
- Add plugin goals: exec, remove & rmi
- Depend on XenoAmess fork of docker-client

## v1.4.20 (January 18 2022)
- Fix building images under WSL
- Fix logging of tag name during building
- groupId changed to 'com.xenoamess.docker'

## v1.4.19 (April 16 2020)
- Added a consistent way to select the tags to be applied to build/push

## v1.4.18 (April 15 2020)
- Change: if `useMavenSettingsForAuth` is configured then this will be the only method used.

## v1.4.17 (April 14 2020)
- Disabled Google Container Registry authentication by default 

## v1.4.16 (March 26 2020)
- Add support for multiple tags in build/push

## v1.4.15 (March 20 2020)
- groupId changed to 'ca.vanzyl'
- Dependency updates

## v1.4.13 (October 15 2019)
- [**closed**] #308 Extend repository validation by allowing port number. [#309](https://github.com/spotify/dockerfile-maven/pull/309)
- [**closed**] doc [#318](https://github.com/spotify/dockerfile-maven/pull/318)

## v1.4.12 (July 29 2019)
- [**closed**] Upgrade docker-client dep from 8.14.5 to 8.16.0 [#307](https://github.com/spotify/dockerfile-maven/pull/307)

## v1.4.11 (July 29 2019)
- [**closed**] Validation of docker repository names [#275](https://github.com/spotify/dockerfile-maven/pull/275)
- [**closed**] Update docker-client version to 8.14.5 [#280](https://github.com/spotify/dockerfile-maven/pull/280)

## 1.4.10 (released January 15 2019)
- Add support for --squash experimental build option ([248][])
- Add support for specifying a custom Dockerfile location ([89][])

[248]: https://github.com/spotify/dockerfile-maven/pull/248
[89]: https://github.com/spotify/dockerfile-maven/pull/89

## 1.4.9 (released October 25 2018)
- Upgrade docker-client dep from 8.14.2 to 8.14.3 to fix spotify/docker-client#1100

## 1.4.8 (released October 23 2018)
- Upgrade docker-client dep from 8.14.0 to 8.14.2
- Upgrade com.sparkjava:spark-core to fix CVE-2018-9159
- Improve documentation on referencing build artifacts

## 1.4.7 (released October 8 2018)
- Fix an ExceptionInInitializerError when plugin is used on Java 11 ([230][])
- change source/target version for compiler from 1.7 to 1.8. This means that
  the plugin will only run on Java 8 and above. ([231])

[230]: https://github.com/spotify/dockerfile-maven/pull/230
[231]: https://github.com/spotify/dockerfile-maven/pull/231

## 1.4.6 (released October 5 2018)

- Support for Java 9 and 10

## 1.3.6 (released September 13 2017)

- Add support for using maven settings.xml file to provide docker authorization ([65][])

[65]: https://github.com/spotify/dockerfile-maven/pull/65

## 1.3.3 (released July 11 2017)

- Add support for supplying build-args (`ARG` in Dockerfile) in pom.xml with
  `<buildArgs>` [41][]

- Allow disabling of Google Container Registry credential checks with
  `-Ddockerfile.googleContainerRegistryEnabled` or
  `<googleContainerRegistryEnabled>false</googleContainerRegistryEnabled>`([43][])


[41]: https://github.com/spotify/dockerfile-maven/pull/41
[43]: https://github.com/spotify/dockerfile-maven/pull/43


## 1.3.2 (released July 10 2017)

- Upgrade to docker-client 8.8.0 ([38][])

- Improved fix for NullPointerException in LoggingProgressHandler ([36][])

[36]: https://github.com/spotify/dockerfile-maven/pull/36
[38]: https://github.com/spotify/dockerfile-maven/pull/38


## 1.3.1 (released June 30 2017)

- Fix NullPointerException in LoggingProgressHandler ([30][])

[30]: https://github.com/spotify/dockerfile-maven/pull/30


## 1.3.0 (released June 5 2017)

- Support for authentication to Google Container Registry ([13][], [17][])

[13]: https://github.com/spotify/dockerfile-maven/pull/13
[17]: https://github.com/spotify/dockerfile-maven/pull/17

## Earlier releases

Please check the [list of commits on Github][commits].

[commits]: https://github.com/spotify/dockerfile-maven/commits/master
