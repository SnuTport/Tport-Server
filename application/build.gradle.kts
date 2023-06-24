dependencies {
    /** Database R2dbc**/
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc:3.0.4")
    implementation("io.r2dbc:r2dbc-spi:0.9.1.RELEASE")
    implementation("io.asyncer:r2dbc-mysql:0.9.1")

    /** jasypt **/
    implementation("com.github.ulisesbocchio:jasypt-spring-boot-starter:3.0.3")

    /** coroutines **/
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactive")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0")

    /** mockk **/
    testImplementation("io.mockk:mockk:1.12.0")
}
