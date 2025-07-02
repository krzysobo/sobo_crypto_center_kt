package com.krzysobo.cryptocenter.view.dh.encrypt

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.krzysobo.cryptocenter.appres.AppRes
import com.krzysobo.cryptocenter.viewmodel.dh.EncryptDhPageVM
import com.krzysobo.cryptocenter.viewmodel.getEncryptDhPageVM
import com.krzysobo.soboapptpl.service.AnyRes
import com.krzysobo.soboapptpl.service.anyResText

@Composable
fun OurKeyPairSourceButtonsWidget() {
    val vm: EncryptDhPageVM = getEncryptDhPageVM()
    val clipboardManager: ClipboardManager = LocalClipboardManager.current

    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier
                .weight(0.33f)
                .align(Alignment.CenterVertically)
                .padding(end = 15.dp),
            text = anyResText(AnyRes(AppRes.string.get_secret_key)),
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp)
        )
        Button(
            modifier = Modifier
                .weight(0.33f)
                .padding(end = 15.dp),
            onClick = {
                vm.doGenerateDhKeyPair()
            }) {
            Text(anyResText(AnyRes(AppRes.string.generate_key_pair)))
        }
        Button(
            modifier = Modifier
                .weight(0.33f)
                .padding(end = 15.dp),
            onClick = {
                vm.doPasteSecretKeyHexFromClipboard(clipboardManager)
            }) {
            Text(anyResText(AnyRes(AppRes.string.paste_our_secret_key)))
        }
        Button(
            modifier = Modifier
                .weight(0.33f)
                .padding(end = 15.dp),
            onClick = {
//                println("Button 2 - paste")
                vm.doPasteOurPublicKeyHexFromClipboard(clipboardManager)
            }) {
            Text(anyResText(AnyRes(AppRes.string.paste_our_public_key)))
        }
    }
}
