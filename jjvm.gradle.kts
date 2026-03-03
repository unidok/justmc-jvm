val jvmVersion = property("justmc.jvm.version").toString()
val jdkVersion = property("justmc.jdk.version").toString()

val jvmPath = "jjvm/justmc-jvm-$jvmVersion.jar"
val jdkPath = "jjvm/justmc-jdk-$jdkVersion.jar"

fun download(url: String) {
    println("Download $url")
    val fileName = url.substringAfterLast('/')
    val outputFile = file("jjvm/$fileName")
    ant.invokeMethod("get", mapOf(
        "src" to url,
        "dest" to outputFile,
        "verbose" to true
    ))
}

fun checkJars() {
    if (!file(jvmPath).exists()) download("https://github.com/unidok/justmc-jvm/releases/download/jvm-$jvmVersion/justmc-jvm-$jvmVersion.jar")
    if (!file(jdkPath).exists()) download("https://github.com/unidok/justmc-jvm/releases/download/jdk-$jdkVersion/justmc-jdk-$jdkVersion.jar")
}

checkJars()

dependencies {
    add("compileOnly", files(jdkPath))
}

tasks.named("jar", Jar::class.java) {
    val outDir = if (hasProperty("justmc.dir.out")) {
        property("justmc.dir.out").toString()
    } else {
        "./jjvm/out"
    }
    destinationDirectory.set(file(outDir))
}

tasks.register("buildModule", JavaExec::class.java) {
    doFirst {
        checkJars()
    }
    group = "justmc"
    dependsOn("jar")
    classpath = files(jvmPath)
    workingDir = file("jjvm")
    args((tasks.named("jar").get() as Jar).archiveFile.get().asFile.absolutePath)
}

tasks.register("uploadModule", JavaExec::class.java) {
    doFirst {
        checkJars()
    }
    group = "justmc"
    dependsOn("jar")
    classpath = files(jvmPath)
    workingDir = file("jjvm")
    args(
        (tasks.named("jar").get() as Jar).archiveFile.get().asFile.absolutePath,
        "-u"
    )
}