plugins {
    id("marine.library-conventions")
}

dependencies {
    implementation(project(":shared"))
    testFixturesImplementation(project(":shared"))
}

testing {
    suites {
        withType<JvmTestSuite> {
            dependencies {
                implementation(project(":shared"))
                implementation(testFixtures(project(":shared")))
            }
        }
    }
}