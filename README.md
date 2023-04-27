It appears that the Kotlin multiplatform Gradle plugin [resolves dependencies during the configuration phase](https://scans.gradle.com/s/qsl2yvg57qi3i/performance/dependency-resolution?toggled=dependency-resolution-configuration-user-initiated#dependency-resolution-configuration-user-initiated-0).

Looking into the detached configuration, it [resolves the Kotlin/Native compiler](https://scans.gradle.com/s/qsl2yvg57qi3i/dependencies?focusedDependency=WzAsMCwwLFswLDAsWzBdXV0&toggled=W1swXSxbMCwwXV0).

The detached configuration eagerly resolves dependencies [here](https://github.com/JetBrains/kotlin/blob/master/libraries/tools/kotlin-gradle-plugin/src/common/kotlin/org/jetbrains/kotlin/gradle/targets/native/NativeCompilerDownloader.kt#L151).

[Dependency resolution during configuration should be avoided](https://docs.gradle.org/current/userguide/performance.html#avoid_dependency_resolution_during_configuration), as dependency resolution is expensive, and the configuration phase runs on every build.

The only steps required to reproduce the issue are to apply the Kotlin multiplatform Gradle plugin, define a target, and ensure that the Kotlin/Native compiler is not already present in `KONAN_HOME` (`~/.konan/`).

Steps to reproduce:
1. `rm -rf ~/.konan` (or wherever your `KONAN_HOME` is located)
2. `./gradlew help --scan`
