plugins {
    id("org.jetbrains.kotlin.multiplatform") version "1.8.21"
}

repositories {
    mavenCentral()
}

kotlin {
    macosArm64()
}
