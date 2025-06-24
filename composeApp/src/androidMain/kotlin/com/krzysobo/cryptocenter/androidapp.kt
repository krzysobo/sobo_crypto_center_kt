package com.krzysobo.cryptocenter

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.krzysobo.cryptocenter.settings.SCC_ROUTE_HANDLE
import com.krzysobo.cryptocenter.settings.soboCryptoCenterRoutes
import com.krzysobo.soboapptpl.service.SoboRouter
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun SoboCryptoCenterAndroidApp() {
    SoboRouter.initRouter(
        routes = soboCryptoCenterRoutes,
        routeHandleWelcome = SCC_ROUTE_HANDLE.WELCOME.value,
        authUsed = false
    )
    SoboRouter.navigateToWelcomeIfBackStackEmpty()

    SoboTheme(useDarkTheme = com.krzysobo.soboapptpl.viewmodel.isDarkMode()) {
        val routesDebug = false
        Column {
            // ---- routes debug ----
            if (routesDebug) {
                Text("BS SIZE: ${SoboRouter.backStack.size}")
                Text("Current route: ${SoboRouter.getCurrentRoute().handle}")
                Text("Previous Route: ${SoboRouter.getPreviousBackStackItemIfAvailable()?.route?.handle ?: "-nope-"}")
            }
            // ---- /routes debug ----

            val activity = (LocalContext.current as? Activity)
            BackHandler(enabled = true) {  // handling the Smartphone's "BACK" button
                com.krzysobo.soboapptpl.service.handleAndroidBackButton(activity)
            }

            com.krzysobo.soboapptpl.widgets.menus.AppLayoutWithDrawerMenu(
                soboCryptoCenterMenuItems,
                {
                    if (it.routeHandle != "") {
                        SoboRouter.navigateToRouteHandle(it.routeHandle)
                    }
                },
                topAppBarTitle = "Sobo Crypto Center v. $appVersion",
                drawerAppTitle = "Sobo Crypto Center",
            )
        }
    }
}
