package com.krzysobo.cryptocenter.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.krzysobo.cryptocenter.appVersion
import com.krzysobo.cryptocenter.appres.AppRes
import com.krzysobo.soboapptpl.service.AnyRes
import com.krzysobo.soboapptpl.service.anyResText
import com.krzysobo.soboapptpl.widgets.PageHeader


@Composable
fun PageAboutSoboCryptoCenter() {
    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(all = 32.dp)
    ) {
        item {
            PageHeader(anyResText(AnyRes(AppRes.string.about_s, arrayOf("Sobo Crypto Center"))))
        }

        item {
            Text("Sobo Crypto Center v. $appVersion")
        }

        item {
            Text("Copyright (c) Krzysztof Sobolewski <krzysztof.sobolewski@gmail.com>")
        }

        item {
            Text("Apache 2.0 License")
        }

        item {
            Text(
                anyResText(
                    AnyRes(
                        AppRes.string.more_at_url_s,
                        arrayOf("https://github.com/krzysobo/sobo_crypto_center_app/blob/main/README.md")
                    )
                )
            )
        }
    }

}