package com.krzysobo.cryptocenter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.krzysobo.soboapptpl.service.LocaleManager


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        LocaleManager.storeOriginalLocale()

        super.onCreate(savedInstanceState)

        setContent {
            LocaleManager.useLocaleFromAppSettings()
            SoboCryptoCenterAndroidApp()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    SoboCryptoCenterAndroidApp()
}
