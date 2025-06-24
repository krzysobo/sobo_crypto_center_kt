rootProject.name = "SoboCryptoCenter"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

//include(":soboCryptoLib")

include("soboAppTpl")
project(":soboAppTpl").projectDir = file("libsrcs/sobo_app_tpl_kt/soboAppTpl")

include("soboCryptoLib")
project(":soboCryptoLib").projectDir = file("libsrcs/sobo_crypto_lib_kt/soboCryptoLib")


include(":composeApp")
//include(":apptpl")
