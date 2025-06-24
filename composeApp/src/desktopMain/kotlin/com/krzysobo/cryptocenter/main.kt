package com.krzysobo.cryptocenter

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.krzysobo.cryptocenter.SoboCryptoCenterDesktopApp

fun main() = application {
    val stateMax = rememberWindowState(placement = WindowPlacement.Maximized)
    val stateWh = rememberWindowState(size= DpSize(800.dp, 800.dp))

    Window(
        onCloseRequest = ::exitApplication,
        title = "App",
        state = stateWh,
    ) {
        SoboCryptoCenterDesktopAppTheme()
    }

}