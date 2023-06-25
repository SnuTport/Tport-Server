dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation(project(":application"))

    /** springdoc-openapi-ui **/
    implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.1.0")
}
