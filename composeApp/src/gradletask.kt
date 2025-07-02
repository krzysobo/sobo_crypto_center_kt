fun generateResourcesObjectFileHashmap(resourceMap: Map<String, Map<String, String>>): String {
    return """
    package com.acme.uifw.generated

    object StringResources {
        val resources: Map<String, Map<String, String>> = mapOf(
    ${


        resourceMap.entries.joinToString(",\n") { (lang, strings) ->
            """"$lang" to mapOf(${
                strings.entries.joinToString(", ") { (key, value) ->
                    """"$key" to "${value.replace("\"", "\\\"")}" """
                }
            })"""
        }
    }
        )
    }
    """.trimIndent()
}

fun generateResourcesObjectFileObject(resourceMap: Map<String, Map<String, String>>): String {
    val tmpMapByHandle = Map<String, Map<String, String>>
    val tmpMapListByHandle = Map<String, MutableList<String>>

    for(lang in resourceMap) {
        for (handle in resourceMap[lang]) {
            if (!tmpMapListByHandle.containsKey(handle) ) {
                tmpMapListByHandle[handle] = mutableListOf()
            }

            tmpMapListByHandle.add("\"${lang}\" to \"${resourceMap[lang][handle]}  \"  ")


            tmpMapByHandle[handle][lang] = resourceMap[lang][handle]
        }
    }

    return """
    package com.krzysobo.cryptocenter.generated

    object StringResourcesTree {
        object string {
    ${ for(handle in tmpMapListByHandle) {
        """ val $handle = SoboStringResource(
           ${tmpMapListByHandle[handle].joinToString(", ")}
        )
        """.trimIndent()
    }

    }}
    """.trimIndent()
}


// Parse strings.xml into a Map<String, String>
fun parseStringsResourceXml(file: File): Map<String, String> {
    val doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file)
    doc.documentElement.normalize()
    val stringNodes = doc.getElementsByTagName("string")
    val result = mutableMapOf<String, String>()
    for (i in 0 until stringNodes.length) {
        val node = stringNodes.item(i) as Element
        val name = node.getAttribute("name")
        val value = node.textContent
        result[name] = value
    }
    return result
}


tasks.register("generateSoboResources") {
    description = "Generate Kotlin code file containing string resources " +
            "from values-XX/strings.xml, stored as SoboStringResource " +
            "instances"
    group = "build"

    doLast() {
        // val file = File("myfile.txt")
        // file.createNewFile()
        // file.writeText("HELLO FROM MY TASK")

        val resourcesDir = file("src/commonMain/composeResources")
        val outputDir = file("$buildDir/generated/source/kmp/main/kotlin/com/krzysobo/cryptocenter/generated")
        val outputFile = file("$outputDir/SoboStringResources.kt")

        // Create output directory for SoboStringResource
        outputDir.mkdirs()

        // Map of language code to resources - here only hashmap,
        // in the generated file it will contain SoboStringResource instances
        val resourceMap = mutableMapOf<String, Map<String, String>>()

        // Default language (values/strings.xml)

        // TODO - probably switch to values/strings.xml for true "defaultness"
        val defaultStringsFile = file("$resourcesDir/values-en/strings.xml")
        if (defaultStringsFile.exists()) {
            resourceMap["en"] = parseStringsResourceXml(defaultStringsFile)
        }

        // files for other languages (values-XX/strings.xml)
        resourcesDir.walk().filter {
            it.isFile && it.path.contains("values-") && it.name == "strings.xml"
        }.forEach { file ->
            val langCode = file.parentFile.name.removePrefix("values-")
            resourceMap[langCode] = parseStringsResourceXml(file)
        }

        // Generate Kotlin file
        outputFile.writeText(generateResourcesObjectFileObject(resourceMap))

    }
}

// Ensure task runs before compilation
//tasks.named("compileKotlinCommonMain") {
tasks.named("generateResourceAccessorsForCommonMain") {
    dependsOn("generateSoboResources")
}


/*
//        resourceMap.entries.joinToString(",\n") { (lang, strings) ->
//            """"$lang" to mapOf(${
//                strings.entries.joinToString(", ") { (key, value) ->
//                    """"$key" to "${value.replace("\"", "\\\"")}" """
//                }
//            })"""
//        }
//    }
//        )
//    }

 */

fun test () {
    val dupa: Map<String, String> = mapOf("a" to "1", "b" to "2")

    println(dupa)

    for ((key, value) in dupa) {
        println("KEY: $key VALUE: $value")
    }
}