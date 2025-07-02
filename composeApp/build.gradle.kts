import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
//import org.w3c.dom.Element
//import javax.xml.parsers.DocumentBuilderFactory

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.serialization)
//    id("generate-sobo-resources")
    id("hello-world-class")
    id("build-acme-resources")
}

//fun generateResourcesObjectFileHashmap(resourceMap: Map<String, Map<String, String>>): String {
//    return """
//    package com.acme.uifw.generated
//
//    object StringResources {
//        val resources: Map<String, Map<String, String>> = mapOf(
//    ${
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
//    """.trimIndent()
//}
//
//fun generateResourcesObjectFileObject(resourceMap: Map<String, Map<String, String>>): String {
//    val tmpMapListByHandle: MutableMap<String, MutableList<String>> = mutableMapOf()
//
//    for ((lang, langDefs) in resourceMap) {
//        for ((handle, langDef) in langDefs) {
//            if (!tmpMapListByHandle.containsKey(handle)) {
//                tmpMapListByHandle[handle] = mutableListOf()
//            }
//            tmpMapListByHandle[handle]!!.add("\"$lang\" to \"\"\"${langDef.trim()}  \"\"\"  ")
//        }
//    }
//
//    var defsOut: MutableList<String> = mutableListOf()
//
//    for ((handle, langDefs) in tmpMapListByHandle) {
//        defsOut.add(
//            "            val $handle = SoboStringResource(hashMapOf(" +
//                    "\n                ${
//                        langDefs.joinToString(
//                            ",\n                "
//                        )
//                    }))\n"
//        )
//    }
//
//    return """
//    package com.krzysobo.cryptocenter.generated
//
//    import com.krzysobo.soboapptpl.service.SoboStringResource
//
//    object SoboRes {
//        const val s = "\${'$'}s"   // avoiding interpolation problem
//        const val d = "\${'$'}d"   // avoiding interpolation problem
//
//        object string { ${"\n" + defsOut.joinToString("\n")}
//    }}
//    """.trimIndent()
//}
//
//
//// Parse strings.xml into a Map<String, String>
//fun parseStringsResourceXml(file: File): Map<String, String> {
//    val doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file)
//    doc.documentElement.normalize()
//    val stringNodes = doc.getElementsByTagName("string")
//    val result = mutableMapOf<String, String>()
//    for (i in 0 until stringNodes.length) {
//        val node = stringNodes.item(i) as Element
//        val name = node.getAttribute("name")
//        val value = node.textContent
//        result[name] = value
//    }
//    return result
//}
//
//
//tasks.register("generateSoboResources") {
//    description = "Generate Kotlin code file containing string resources " +
//            "from values-XX/strings.xml, stored as SoboStringResource " +
//            "instances"
//    group = "build"
//
//    doLast() {
//        // val file = File("myfile.txt")
//        // file.createNewFile()
//        // file.writeText("HELLO FROM MY TASK")
//
//        val resourcesDir = file("src/commonMain/composeResources")
//        val outputDir =
//            file("$buildDir/generated/source/kmp/main/kotlin/com/krzysobo/cryptocenter/generated")
//        val outputFile = file("$outputDir/SoboStringResources.kt")
//
//        // Create output directory for SoboStringResource
//        outputDir.mkdirs()
//
//        // Map of language code to resources - here only hashmap,
//        // in the generated file it will contain SoboStringResource instances
//        val resourceMap = mutableMapOf<String, Map<String, String>>()
//
//        // Default language (values/strings.xml)
//
//        // TODO - probably switch to values/strings.xml for true "defaultness"
//        val defaultStringsFile = file("$resourcesDir/values-en/strings.xml")
//        if (defaultStringsFile.exists()) {
//            resourceMap["en"] = parseStringsResourceXml(defaultStringsFile)
//        }
//
//        // files for other languages (values-XX/strings.xml)
//        resourcesDir.walk().filter {
//            it.isFile && it.path.contains("values-") && it.name == "strings.xml"
//        }.forEach { file ->
//            val langCode = file.parentFile.name.removePrefix("values-")
//            resourceMap[langCode] = parseStringsResourceXml(file)
//        }
//
//        // Generate Kotlin file
//        outputFile.writeText(generateResourcesObjectFileObject(resourceMap))
//
//    }
//}

// Ensure task runs before compilation
//tasks.named("compileKotlinCommonMain") {
//tasks.named("generateResourceAccessorsForCommonMain") {
//    dependsOn("generateSoboResources")
//}




kotlin {

    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    jvm("desktop")


    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)

            // using API instead of implementation to have access to resources
            api(project(":soboCryptoLib", ""))
            api(project(":soboAppTpl", ""))
        }

        val commonMain by getting() {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)
                implementation(libs.androidx.lifecycle.viewmodel)
                implementation(libs.androidx.lifecycle.runtime.compose)
                implementation(compose.materialIconsExtended)  // icons for desktop/others
                implementation(libs.multiplatform.settings)

                implementation(project(":soboCryptoLib", ""))
                implementation(project(":soboAppTpl", ""))
            }
            kotlin.srcDir("$buildDir/generated/source/kmp/main/kotlin")
        }

        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
            implementation(compose.materialIconsExtended)  // icons for desktop/others

            implementation(project(":soboCryptoLib", ""))
            implementation(project(":soboAppTpl", ""))
        }
    }
}

android {
    namespace = "com.krzysobo.cryptocenter"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.krzysobo.cryptocenter"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.2.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            /*
                --> fix for the following BouncyCastle's error:
                2 files found with path 'META-INF/versions/9/OSGI-INF/MANIFEST.MF' from inputs:
                 - org.bouncycastle:bcprov-jdk18on:1.81/bcprov-jdk18on-1.81.jar
                 - org.jspecify:jspecify:1.0.0/jspecify-1.0.0.jar
                Adding a packaging block may help, please refer to
             */
            excludes += "META-INF/versions/9/OSGI-INF/MANIFEST.MF"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }


}

dependencies {
    implementation(libs.androidx.ui.android)
    implementation(libs.androidx.ui.text.android)
    implementation(libs.androidx.foundation.layout.android)
    implementation(libs.foundation.layout.android)
    implementation(libs.play.services.auth)
    implementation(libs.ui.android)    // for Android

    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "com.krzysobo.cryptocenter.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.krzysobo.cryptocenter"
            packageVersion = "1.2.0"
        }
    }
}
