package com.krzysobo.cryptocenter

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.KeyOff
import androidx.compose.material.icons.filled.Settings
import com.krzysobo.cryptocenter.appres.AppRes
import com.krzysobo.cryptocenter.settings.SCC_ROUTE_HANDLE
import com.krzysobo.soboapptpl.pubres.PubRes
import com.krzysobo.soboapptpl.service.AnyRes
import com.krzysobo.soboapptpl.widgets.menus.SoboMenuItem


val soboCryptoCenterMenuItems: List<SoboMenuItem> = listOf(
    SoboMenuItem(
        "encrypt",
        AnyRes(AppRes.string.encrypt),
        Icons.Default.Key,
        routeHandle = SCC_ROUTE_HANDLE.ENCRYPT.value
    ),
    SoboMenuItem(
        "decrypt",
        AnyRes(AppRes.string.decrypt),
        Icons.Default.KeyOff,
        routeHandle = SCC_ROUTE_HANDLE.DECRYPT.value
    ),

    SoboMenuItem("div0", null, null),

    SoboMenuItem(
        "encrypt_dh",
        AnyRes(AppRes.string.encrypt_dh),
        Icons.Default.Key,
        routeHandle = SCC_ROUTE_HANDLE.ENCRYPT_DH.value
    ),
    SoboMenuItem(
        "decrypt_dh",
        AnyRes(AppRes.string.decrypt_dh),
        Icons.Default.KeyOff,
        routeHandle = SCC_ROUTE_HANDLE.DECRYPT_DH.value
    ),

    SoboMenuItem("div1", null, null),

    SoboMenuItem(
        "about",
        AnyRes(PubRes.string.about),
        Icons.Default.Info,
        routeHandle = SCC_ROUTE_HANDLE.ABOUT.value
    ),
    SoboMenuItem(
        "help",
        AnyRes(PubRes.string.help),
        Icons.AutoMirrored.Filled.Help,
        routeHandle = SCC_ROUTE_HANDLE.HELP.value
    ),

    SoboMenuItem(
        "settings",
        AnyRes(PubRes.string.settings),
        Icons.Default.Settings,
        routeHandle = SCC_ROUTE_HANDLE.SETTINGS.value
    ),
)
