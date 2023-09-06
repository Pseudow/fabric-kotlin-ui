plugins {
    kotlin("jvm")
    `java-library`
}

group = "net.alfheim.internal.kotlinui"
version = "1.0.1"

kotlin {
    jvmToolchain(17)
}

java {
    withSourcesJar()
    withJavadocJar()

    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}


tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
