package com.krzysobo.cryptocenter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import com.krzysobo.soboapptpl.service.LocaleManager
import java.util.Locale


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        LocaleManager.storeOriginalLocale()

        super.onCreate(savedInstanceState)

        setContent {
//            println("TESTXX DEFAULT LOCALE LANGUAGE BEFORE: ${Locale.getDefault().language}")

            LocaleManager.useLocaleFromAppSettings()
            SoboCryptoCenterAndroidApp()
//            println("TESTXX DEFAULT LOCALE LANGUAGE AFTER: ${Locale.getDefault().language}")

            // ON THIS PRINT HANGS THE KEEPING OF THE SELECTED LOCALE!!! WEIRD!!!
            println("TESTXX -> MainActivity -> LANGUAGE in LocalConfiguration - not related to Default --: locale: ${LocalConfiguration.current.locale.language} locales: ${LocalConfiguration.current.locales[0].language}")
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    SoboCryptoCenterAndroidApp()
}
