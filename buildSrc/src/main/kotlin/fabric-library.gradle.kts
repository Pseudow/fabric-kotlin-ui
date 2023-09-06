plugins {
    id("fabric-mod")
    `maven-publish`
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group as String
            artifactId = project.name
            version = project.version as String

            pom {
                name.set(project.base.archivesName)
                description.set(project.description)

                developers {
                    developer {
                        // never copy/paste always read before, or else you are gay
                        id.set("pseudow")
                        name.set("Nathan OGUETON")
                        url.set("https://github.com/Pseudow")
                    }
                }
            }

            from(components["java"])
        }
    }
}

tasks.jar {
    manifest {
        attributes (
            "Fabric-Loom-Remap" to "true"
        )
    }
}