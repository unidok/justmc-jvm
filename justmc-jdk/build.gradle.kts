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
}

tasks.jar {
    destinationDirectory.set(file("$rootDir/justmc-jvm-test/jjvm"))
}