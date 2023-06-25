import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.1.1" apply false
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("jvm") version "1.8.22"
    kotlin("plugin.spring") version "1.8.22"
    kotlin("plugin.jpa") version "1.8.22"
    kotlin("plugin.allopen") version "1.8.22"
    id("org.jlleitschuh.gradle.ktlint") version "10.3.0"
}

group = "kr.ac.snu"
version = "0.0.1-SNAPSHOT"

java.sourceCompatibility = JavaVersion.VERSION_17

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply {
        plugin("kotlin")
        plugin("org.jlleitschuh.gradle.ktlint")
        plugin("org.jetbrains.kotlin.jvm")
        plugin("io.spring.dependency-management")
        plugin("kotlin-spring")
        plugin("kotlin-jpa")
        plugin("kotlin-allopen")
    }

    ktlint {
        disabledRules.set(setOf("no-wildcard-imports"))
    }

    dependencyManagement {
        imports {
            mavenBom("org.springframework.boot:spring-boot-dependencies:3.0.2")
            mavenBom("org.jetbrains.kotlinx:kotlinx-coroutines-bom:1.5.2")
        }

        dependencies {
            dependency("com.fasterxml.jackson.module:jackson-module-kotlin:2.12.4")
            dependency("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.12.4")
        }
    }

    dependencies {
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.12.4")
        implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.12.4")
        implementation("org.springframework.boot:spring-boot-starter")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += "-Xjsr305=strict"
            jvmTarget = JavaVersion.VERSION_17.toString()
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}
