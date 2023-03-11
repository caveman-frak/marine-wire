plugins {
    id("marine.java-conventions")
    id("java")
    id("io.freefair.lombok") version "8.0.0-rc2"
    id("io.spring.dependency-management") version "1.1.0"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":shared"))
//    testImplementation("org.junit.jupiter:junit-jupiter")
//    testImplementation("org.assertj:assertj-core")
//    testImplementation("org.mockito:mockito-core")
}
