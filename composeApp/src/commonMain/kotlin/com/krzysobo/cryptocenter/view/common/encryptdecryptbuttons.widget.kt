package com.krzysobo.cryptocenter.view.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.krzysobo.cryptocenter.appres.AppRes
import com.krzysobo.cryptocenter.settings.SCC_ROUTE_HANDLE
import com.krzysobo.soboapptpl.service.AnyRes
import com.krzysobo.soboapptpl.service.SoboRouter
import com.krzysobo.soboapptpl.service.anyResText

@Composable
fun encryptDecryptSwitcherButtons(btnSelected: String = "encrypt") {
    val btnColorsSelected = ButtonDefaults.buttonColors(
        backgroundColor = Color.Blue,
        contentColor = Color.White
    )

    val btnColorsNotSelected = ButtonDefaults.buttonColors(
        backgroundColor = Color.White,
        contentColor = Color.Blue
    )

    val textStyleSelected = TextStyle(
        fontWeight = FontWeight.Bold,
        textDecoration = TextDecoration.Underline
    )

    val textStyleNotSelected = TextStyle()

    Row {
        Button(
            modifier = Modifier
                .weight(0.5f)
                .padding(end = 15.dp),
            colors = if (btnSelected == "encrypt") btnColorsSelected else btnColorsNotSelected,
            onClick = {
                SoboRouter.navigateToRouteHandle(SCC_ROUTE_HANDLE.ENCRYPT.value)

            }) {
            Text(
                modifier = Modifier,
                style = if (btnSelected == "encrypt") textStyleSelected else textStyleNotSelected,
                text = anyResText(AnyRes(AppRes.string.encrypt))
            )
        }

        Button(
            modifier = Modifier
                .weight(0.5f)
                .padding(end = 0.dp),
            colors = if (btnSelected == "decrypt") btnColorsSelected else btnColorsNotSelected,
            onClick = {
                SoboRouter.navigateToRouteHandle(SCC_ROUTE_HANDLE.DECRYPT.value)
            }) {
            Text(
                modifier = Modifier,
                style = if (btnSelected == "encrypt") textStyleSelected else textStyleNotSelected,
                text = anyResText(AnyRes(AppRes.string.decrypt))
            )

        }

    }
}