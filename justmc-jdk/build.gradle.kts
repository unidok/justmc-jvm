plugins {
    java
}

group = "me.unidok"
version = "2.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains:annotations:24.0.0")
}

java {
    withSourcesJar()
}

tasks.named<Jar>("sourcesJar") {
    destinationDirectory.set(file("$rootDir/justmc-jvm-test/libs"))
}

tasks.jar {
    destinationDirectory.set(file("$rootDir/justmc-jvm-test/libs"))
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