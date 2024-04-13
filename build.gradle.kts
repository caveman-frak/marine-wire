plugins {
    id("marine.library-conventions")
}

dependencies {
    implementation(project(":shared"))
    testImplementation(project(":test"))
    implementation("org.locationtech.spatial4j:spatial4j:0.8")
    implementation("org.springdoc:springdoc-openapi-starter-common:2.5.0")
}