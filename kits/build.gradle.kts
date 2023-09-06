plugins {
    `fabric-library`
}

description = "Set of kits allowing developers to build efficiently and fast UI on Alfheim!"

dependencies {
    compileOnlyApi(project(":api", configuration = "namedElements"))
}