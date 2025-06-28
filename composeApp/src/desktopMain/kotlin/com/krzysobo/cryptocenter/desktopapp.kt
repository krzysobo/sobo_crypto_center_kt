package com.krzysobo.cryptocenter


import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.window.FrameWindowScope
import sobocryptocenter.composeapp.generated.resources.Res
import sobocryptocenter.composeapp.generated.resources.app_name_sobo_crypto_center_desktop
import com.krzysobo.cryptocenter.settings.SCC_ROUTE_HANDLE
import com.krzysobo.cryptocenter.settings.soboCryptoCenterRouteHandlesForDesktopMenu
import com.krzysobo.cryptocenter.settings.soboCryptoCenterRoutes
import com.krzysobo.soboapptpl.service.LocaleManager
import com.krzysobo.soboapptpl.service.SoboRouter
import com.krzysobo.soboapptpl.widgets.menus.PageTabsWithOutletAndLogin
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun SoboCryptoCenterDesktopApp() {
    LocaleManager.useLocaleFromAppSettings()

    SoboRouter.initRouter(
        routes = soboCryptoCenterRoutes,
        routeHandleLoggedInUserHome = "",
        routeHandleWelcome = SCC_ROUTE_HANDLE.WELCOME.value,
        authUsed = false  // no auth needed in this app!
    )

    SoboTheme {
        Column {
            Text(stringResource(Res.string.app_name_sobo_crypto_center_desktop))
            PageTabsWithOutletAndLogin(
                soboCryptoCenterRouteHandlesForDesktopMenu,
                soboCryptoCenterRoutes
            )
        }
    }
}


@Composable
fun FrameWindowScope.DesktopApp() {
    SoboTheme {
        Column {
            Column {
                SoboCryptoCenterDesktopApp()
            }
        }
    }
}
