plugins {
    java
    kotlin("jvm")
}

group = "me.unidok"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}