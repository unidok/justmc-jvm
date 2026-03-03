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

tasks.register<Exec>("publishReleaseJDK") {
    doFirst {
        require(System.getenv("GH_TOKEN") != null) { "env GH_TOKEN is null" }
    }
    group = "publishing"
    dependsOn("jar")
    val tagName = "jdk-$version"
    val jarFile = tasks.jar.get().archiveFile.get().asFile
    commandLine(
        "gh", "release", "create", tagName,
        jarFile.absolutePath,
        "--repo", "unidok/justmc-jvm",
        "--title", tagName,
    )
}