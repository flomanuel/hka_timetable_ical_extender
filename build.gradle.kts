/*
 * Copyright (c) 2024 - present Florian Sauer
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation
 * files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT
 * LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

plugins {
    java
    idea
    checkstyle
    id("org.springframework.boot") version libs.versions.springBootPlugin.get()
    id("io.spring.dependency-management") version libs.versions.springDependencyManagementPlugin.get()
}

group = "de.florian-emanuel-sauer"
version = "1"

val javaVersion: String = System.getProperty("java") ?: libs.versions.javaVersion.get()

java {
    sourceCompatibility = JavaVersion.toVersion(javaVersion)
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("net.sf.biweekly:biweekly:${libs.versions.biweekly.get()}")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

// Gradle Task: build > assemble
tasks.named("bootJar", org.springframework.boot.gradle.tasks.bundling.BootJar::class.java) {
    doLast {
        println(
            """
            |
            |Call of executable JAR-file:
            |Production:
            |   java -jar build/libs/${project.name}-${project.version}.jar --spring.profiles.active=production
            |
            |Development:
            |   java -jar build/libs/${project.name}-${project.version}.jar --spring.profiles.active=dev
            """.trimMargin("|")
        )
    }
}
