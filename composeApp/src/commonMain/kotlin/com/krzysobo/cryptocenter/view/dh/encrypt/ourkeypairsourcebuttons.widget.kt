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
import sobocryptocenter.composeapp.generated.resources.Res
import sobocryptocenter.composeapp.generated.resources.generate_random
import sobocryptocenter.composeapp.generated.resources.get_secret_key
import sobocryptocenter.composeapp.generated.resources.paste
import com.krzysobo.soboapptpl.service.AnyRes
import com.krzysobo.soboapptpl.service.anyResText
import com.krzysobo.cryptocenter.viewmodel.EncryptPageVM
import com.krzysobo.cryptocenter.viewmodel.dh.EncryptDhPageVM
import com.krzysobo.cryptocenter.viewmodel.getEncryptDhPageVM
import com.krzysobo.cryptocenter.viewmodel.getEncryptPageVM
import sobocryptocenter.composeapp.generated.resources.generate_key_pair
import sobocryptocenter.composeapp.generated.resources.paste_our_public_key
import sobocryptocenter.composeapp.generated.resources.paste_our_secret_key

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
            text = anyResText(AnyRes(Res.string.get_secret_key)),
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp)
        )
        Button(
            modifier = Modifier
                .weight(0.33f)
                .padding(end = 15.dp),
            onClick = {
                vm.doGenerateDhKeyPair()
            }) {
            Text(anyResText(AnyRes(Res.string.generate_key_pair)))
        }
        Button(
            modifier = Modifier
                .weight(0.33f)
                .padding(end = 15.dp),
            onClick = {
                vm.doPasteSecretKeyHexFromClipboard(clipboardManager)
            }) {
            Text(anyResText(AnyRes(Res.string.paste_our_secret_key)))
        }
        Button(
            modifier = Modifier
                .weight(0.33f)
                .padding(end = 15.dp),
            onClick = {
//                println("Button 2 - paste")
                vm.doPasteOurPublicKeyHexFromClipboard(clipboardManager)
            }) {
            Text(anyResText(AnyRes(Res.string.paste_our_public_key)))
        }
    }
}
