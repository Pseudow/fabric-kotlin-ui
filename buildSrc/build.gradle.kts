plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    mavenCentral()
    maven {
        name = "Fabric"
        url = uri("https://maven.fabricmc.net/")
    }
}

dependencies {
    implementation("fabric-loom:fabric-loom.gradle.plugin:latest.release")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.10")
}
