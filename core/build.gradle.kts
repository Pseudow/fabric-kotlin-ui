plugins {
    `fabric-library`
}

description = "Implementation of the api allowing developers to create different types of UI in Alfheim!"

dependencies {
    api(project(":api", configuration = "namedElements"))
    api(project(":kits", configuration = "namedElements"))
}