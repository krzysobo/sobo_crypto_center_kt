package com.krzysobo.cryptocenter.settings

import apptpl.composeapp.generated.resources.Res
import apptpl.composeapp.generated.resources.about
import apptpl.composeapp.generated.resources.decrypt
import apptpl.composeapp.generated.resources.encrypt
import apptpl.composeapp.generated.resources.help
import apptpl.composeapp.generated.resources.settings
import apptpl.composeapp.generated.resources.welcome
import com.krzysobo.soboapptpl.service.AnyRes
import com.krzysobo.soboapptpl.service.SoboRoute
import com.krzysobo.cryptocenter.view.PageAboutSoboCryptoCenter
import com.krzysobo.cryptocenter.view.PageHelpSoboCryptoCenter
import com.krzysobo.cryptocenter.view.PageSoboCryptoCenterDecrypt
import com.krzysobo.cryptocenter.view.PageSoboCryptoCenterEncrypt
import com.krzysobo.cryptocenter.view.PageSoboCryptoCenterSettings
import com.krzysobo.cryptocenter.view.PageWelcomeSoboCryptoCenter


enum class SCC_ROUTE_HANDLE(val value: String) {
    WELCOME("welcome"),
    ABOUT("about"),
    HELP("help"),
    ENCRYPT("encrypt"),
    DECRYPT("decrypt"),
    SETTINGS("settings"),
}

val soboCryptoCenterRouteHandlesForDesktopMenu = listOf(
    SCC_ROUTE_HANDLE.ABOUT.value,
    SCC_ROUTE_HANDLE.HELP.value,
    SCC_ROUTE_HANDLE.ENCRYPT.value,
    SCC_ROUTE_HANDLE.DECRYPT.value,
    SCC_ROUTE_HANDLE.SETTINGS.value,
)

val soboCryptoCenterRoutes = listOf(
    SoboRoute(
        SCC_ROUTE_HANDLE.WELCOME.value,
        AnyRes(Res.string.welcome),
        { PageWelcomeSoboCryptoCenter() },
        perms = listOf()
    ),
    SoboRoute(
        SCC_ROUTE_HANDLE.ENCRYPT.value,
        AnyRes(Res.string.encrypt),
        { PageSoboCryptoCenterEncrypt() }
    ),

    SoboRoute(
        SCC_ROUTE_HANDLE.DECRYPT.value,
        AnyRes(Res.string.decrypt),
        { PageSoboCryptoCenterDecrypt() }
    ),
    SoboRoute(
        SCC_ROUTE_HANDLE.SETTINGS.value,
        AnyRes(Res.string.settings),
        { PageSoboCryptoCenterSettings() }
    ),
    SoboRoute(
        SCC_ROUTE_HANDLE.ABOUT.value,
        AnyRes(Res.string.about),
        { PageAboutSoboCryptoCenter() },
    ),
    SoboRoute(
        SCC_ROUTE_HANDLE.HELP.value,
        AnyRes(Res.string.help),
        { PageHelpSoboCryptoCenter() },
    ),


)
