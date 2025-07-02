package com.krzysobo.cryptocenter.view.encrypt

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
import com.krzysobo.cryptocenter.viewmodel.EncryptPageVM
import com.krzysobo.cryptocenter.viewmodel.getEncryptPageVM
import com.krzysobo.soboapptpl.service.AnyRes
import com.krzysobo.soboapptpl.service.anyResText

@Composable
fun KeySourceButtonsWidget() {
    val vm: EncryptPageVM = getEncryptPageVM()
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
//                println("Button 1 - random")
                vm.closeSecretKeyHexSrcPassWidget()
                vm.doGenerateRandomKey()
            }) {
            Text(anyResText(AnyRes(AppRes.string.generate_random)))
        }
        Button(
            modifier = Modifier
                .weight(0.33f)
                .padding(end = 15.dp),
            onClick = {
//                println("Button 2 - paste")
                vm.closeSecretKeyHexSrcPassWidget()
                vm.doPasteKeyHexFromClipboard(clipboardManager)
            }) {
            Text(anyResText(AnyRes(AppRes.string.paste)))
        }
//        Button(
//            modifier = Modifier
//                .weight(0.25f)
//                .padding(end = 15.dp),
//            onClick = {
////                println("Button 3 - from password")
//                vm.openSecretKeyHexSrcPassWidget()
//            }) {
//            Text(anyResText(AnyRes(AppRes.string.from_password)))
//        }
    }
}
