import org.gradle.kotlin.dsl.java

plugins {
    java
    kotlin("jvm") version "2.3.10"
}

apply("https://raw.githubusercontent.com/unidok/justmc-jvm/refs/heads/master/jjvm.gradle.kts")

group = "me.yourname"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(21)
}
