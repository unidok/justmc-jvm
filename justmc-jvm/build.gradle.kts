plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

group = "me.unidok"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":justmc-code"))
    implementation("org.ow2.asm:asm:9.9")
    implementation("org.ow2.asm:asm-tree:9.9")
    implementation("org.ow2.asm:asm-util:9.9")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")
    implementation(kotlin("stdlib"))
}

kotlin {
    jvmToolchain(21)
}

tasks.jar {
    destinationDirectory.set(file("$rootDir/justmc-jvm-test/jjvm"))
    manifest.attributes["Main-Class"] = "me.unidok.jjvm.Main"
    doFirst {
        from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    }
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}