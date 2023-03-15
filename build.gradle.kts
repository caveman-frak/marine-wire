plugins {
    id("marine.library-conventions")
}

dependencies {
    implementation(project(":shared"))
    testImplementation(project(":test"))
    implementation("org.locationtech.spatial4j:spatial4j:0.8")
}