import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "ru.ifmo.observable"

plugins {
    kotlin("jvm") version Versions.kotlin
    `build-scan`
    id("org.jlleitschuh.gradle.ktlint") version Versions.ktlint
    id("com.github.ben-manes.versions") version Versions.gradle_versions
    id("fr.brouillard.oss.gradle.jgitver") version Versions.jgitver
    `maven-publish`
}

repositories {
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
}

buildScan {
    termsOfServiceUrl = "https://gradle.com/terms-of-service"
    termsOfServiceAgree = "yes"
}

ktlint {
    ignoreFailures.set(true)
}

jgitver {
    strategy("MAVEN")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
    repositories {
        maven(url = "$buildDir/repository")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.wrapper {
    gradleVersion = "5.4.1"
    distributionType = Wrapper.DistributionType.ALL
}

defaultTasks("clean", "build")
