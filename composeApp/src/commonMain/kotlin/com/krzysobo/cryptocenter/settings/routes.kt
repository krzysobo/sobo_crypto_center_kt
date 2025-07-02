package com.krzysobo.cryptocenter.settings

import com.krzysobo.cryptocenter.appres.AppRes
import sobocryptocenter.composeapp.generated.resources.Res
import sobocryptocenter.composeapp.generated.resources.about
import sobocryptocenter.composeapp.generated.resources.decrypt
import sobocryptocenter.composeapp.generated.resources.encrypt
import sobocryptocenter.composeapp.generated.resources.help
import sobocryptocenter.composeapp.generated.resources.settings
import sobocryptocenter.composeapp.generated.resources.welcome
import com.krzysobo.soboapptpl.service.AnyRes
import com.krzysobo.soboapptpl.service.SoboRoute
import com.krzysobo.cryptocenter.view.PageAboutSoboCryptoCenter
import com.krzysobo.cryptocenter.view.PageHelpSoboCryptoCenter
import com.krzysobo.cryptocenter.view.PageSoboCryptoCenterDecrypt
import com.krzysobo.cryptocenter.view.PageSoboCryptoCenterEncrypt
import com.krzysobo.cryptocenter.view.PageSoboCryptoCenterSettings
import com.krzysobo.cryptocenter.view.PageWelcomeSoboCryptoCenter
import com.krzysobo.cryptocenter.view.dh.PageSoboCryptoCenterDecryptDh
import com.krzysobo.cryptocenter.view.dh.PageSoboCryptoCenterEncryptDh
import com.krzysobo.soboapptpl.pubres.PubRes
import sobocryptocenter.composeapp.generated.resources.decrypt_dh
import sobocryptocenter.composeapp.generated.resources.dh_ops
import sobocryptocenter.composeapp.generated.resources.encrypt_dh


enum class SCC_ROUTE_HANDLE(val value: String) {
    WELCOME("welcome"),
    ABOUT("about"),
    HELP("help"),
    ENCRYPT("encrypt"),
    DECRYPT("decrypt"),
    ENCRYPT_DH("encrypt_dh"),
    DECRYPT_DH("decrypt_dh"),
    SETTINGS("settings"),
}

val soboCryptoCenterRouteHandlesForDesktopMenu = listOf(
    SCC_ROUTE_HANDLE.ABOUT.value,
    SCC_ROUTE_HANDLE.HELP.value,
    SCC_ROUTE_HANDLE.ENCRYPT.value,
    SCC_ROUTE_HANDLE.DECRYPT.value,
    SCC_ROUTE_HANDLE.ENCRYPT_DH.value,
    SCC_ROUTE_HANDLE.SETTINGS.value,
)

val soboCryptoCenterRoutes = listOf(
    SoboRoute(
        SCC_ROUTE_HANDLE.WELCOME.value,
        AnyRes(PubRes.string.welcome),
        { PageWelcomeSoboCryptoCenter() },
        perms = listOf()
    ),
    SoboRoute(
        SCC_ROUTE_HANDLE.ENCRYPT.value,
        AnyRes(AppRes.string.encrypt),
        { PageSoboCryptoCenterEncrypt() }
    ),
    SoboRoute(
        SCC_ROUTE_HANDLE.DECRYPT.value,
        AnyRes(AppRes.string.decrypt),
        { PageSoboCryptoCenterDecrypt() }
    ),

    SoboRoute(
        SCC_ROUTE_HANDLE.ENCRYPT_DH.value,
        AnyRes(AppRes.string.dh_ops),
        { PageSoboCryptoCenterEncryptDh() }
    ),
    SoboRoute(
        SCC_ROUTE_HANDLE.DECRYPT_DH.value,
        AnyRes(AppRes.string.decrypt_dh),
        { PageSoboCryptoCenterDecryptDh() }
    ),

    SoboRoute(
        SCC_ROUTE_HANDLE.SETTINGS.value,
        AnyRes(PubRes.string.settings),
        { PageSoboCryptoCenterSettings() }
    ),
    SoboRoute(
        SCC_ROUTE_HANDLE.ABOUT.value,
        AnyRes(PubRes.string.about),
        { PageAboutSoboCryptoCenter() },
    ),
    SoboRoute(
        SCC_ROUTE_HANDLE.HELP.value,
        AnyRes(PubRes.string.help),
        { PageHelpSoboCryptoCenter() },
    ),


)
