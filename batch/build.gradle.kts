plugins {
    id("org.springframework.boot") version "3.1.1"
}

dependencies {
    implementation(project(":application"))

    /**
     * excel
     */
    implementation("org.apache.poi:poi:5.0.0")
    implementation("org.apache.poi:poi-ooxml:5.0.0")
}
