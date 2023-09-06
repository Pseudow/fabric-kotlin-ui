plugins {
    id("kotlin-common")
    id("fabric-loom")
}

base {
    project.parent?.let {
        archivesName.set("${it.name}_${project.name}")
    }
}

loom {
    runConfigs.configureEach {
        ideConfigGenerated(false)
    }
}

repositories {
    mavenLocal()
}

dependencies {
    // Fabric
    minecraft("com.mojang:minecraft:${properties["minecraft_version"]}")
    mappings("net.fabricmc:yarn:${properties["yarn_mappings"]}:v2")

    modCompileOnly("net.fabricmc.fabric-api:fabric-api:${properties["fabric_version"]}")
    modCompileOnly("net.fabricmc:fabric-loader:${properties["loader_version"]}")
}