val jvmVersion = property("justmc.jvm.version").toString()
val jdkVersion = property("justmc.jdk.version").toString()

dependencies {
    add("compileOnly", files("jjvm/justmc-jdk-$jdkVersion.jar"))
}

tasks.named("jar", Jar::class.java) {
    val outDir = if (project.hasProperty("justmc.dir.out")) {
        project.property("justmc.dir.out").toString()
    } else {
        "./jjvm/out"
    }
    destinationDirectory.set(file(outDir))
}

tasks.register("buildModule", JavaExec::class.java) {
    group = "justmc"
    dependsOn("jar")
    classpath = files("jjvm/justmc-jvm-$jvmVersion.jar")
    workingDir = file("jjvm")
    args((tasks.named("jar").get() as Jar).archiveFile.get().asFile.absolutePath)
}

tasks.register("uploadModule", JavaExec::class.java) {
    group = "justmc"
    dependsOn("jar")
    classpath = files("jjvm/justmc-jvm-$jvmVersion.jar")
    workingDir = file("jjvm")
    args(
        (tasks.named("jar").get() as Jar).archiveFile.get().asFile.absolutePath,
        "-u"
    )
}

tasks.register("downloadJars") {
    group = "justmc"
    doLast {
        project.file("jjvm").mkdirs()
        listOf(
            "https://github.com/unidok/JMC-Codespace/releases/download/1.5/JMC-Codespace-1.5.jar",
            "https://github.com/unidok/JMC-Codespace/releases/download/1.4/JMC-Codespace-1.4.jar"
        ).forEach { url ->
            val fileName = url.substringAfterLast("/")
            val outputFile = project.file("jjvm/$fileName")
            ant.invokeMethod("get", mapOf(
                "src" to url,
                "dest" to outputFile,
                "verbose" to true
            ))
        }
    }
}