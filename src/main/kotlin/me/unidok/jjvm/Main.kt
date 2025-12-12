@file:JvmName("Main")

package me.unidok.jjvm

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import java.io.File
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration

fun main(args: Array<out String>) {
    try {
        val path = args[0]
        val uploadFlag = "-u" in args
        val moduleFlag = "-m" in args
        val handlers = JarTranslator(path).translate(moduleFlag).serialize().toString()

        if (uploadFlag) {
            println("Uploading...")
            val client = HttpClient.newBuilder().build()
            val request = HttpRequest.newBuilder()
                .uri(URI("https://m.justmc.ru/api/upload"))
                .POST(HttpRequest.BodyPublishers.ofString(handlers))
                .timeout(Duration.ofSeconds(10))
                .build()
            val response = client.send(request, HttpResponse.BodyHandlers.ofString())
            val body = Json.parseToJsonElement(response.body()).jsonObject
            val statusCode = response.statusCode()
            if (statusCode != 200) {
                println("HTTP $statusCode: ${body["error"]?.jsonPrimitive?.content}")
                return
            }
            val url = "https://m.justmc.ru/api/" + body["id"]!!.jsonPrimitive.content
            if (moduleFlag) {
                println("/module loadUrl $url")
            } else {
                println("/module loadUrl force $url")
            }
        } else {
            val file = File(path.substringBeforeLast('.') + ".json")
            file.createNewFile()
            file.writeText(handlers)
            println("Saved to ${file.absolutePath}")
        }
    } catch (e: Throwable) {
        println(e)
        e.printStackTrace()
    }
}