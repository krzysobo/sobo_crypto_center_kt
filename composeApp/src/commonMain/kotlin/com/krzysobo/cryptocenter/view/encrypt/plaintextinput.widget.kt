package com.krzysobo.cryptocenter.view.encrypt

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentPaste
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import sobocryptocenter.composeapp.generated.resources.Res
import sobocryptocenter.composeapp.generated.resources.paste_plaintext
import sobocryptocenter.composeapp.generated.resources.plaintext
import sobocryptocenter.composeapp.generated.resources.plaintext_required
import com.krzysobo.soboapptpl.service.AnyRes
import com.krzysobo.soboapptpl.service.anyResText
import com.krzysobo.soboapptpl.widgets.TextFieldWithErrorsKeyboardSettings
import com.krzysobo.cryptocenter.viewmodel.EncryptPageVM
import com.krzysobo.cryptocenter.viewmodel.getEncryptPageVM

@Composable
fun PlainTextInputWidget() {
    val focusManager = LocalFocusManager.current
    val vm: EncryptPageVM = getEncryptPageVM()
    val clipboardManager: ClipboardManager = LocalClipboardManager.current

    Row(modifier = Modifier.fillMaxWidth()) {
        TextFieldWithErrorsKeyboardSettings(
            value = vm.plaintext.value,
            onValueChanges = { data: String ->
                vm.plaintext.value = data
                vm.clearPlaintextError()
            },
            modifier = Modifier.padding(all = 10.dp).weight(0.9f),
            labelText = anyResText(AnyRes(Res.string.plaintext)),
            placeHolderText = anyResText(AnyRes(Res.string.plaintext)),
            leadingIcon = null,
            isError = vm.isErrorPlaintext.value,
            errorText = anyResText(AnyRes(Res.string.plaintext_required)),
            focusManager = focusManager,
            singleLine = false,
            minLines = 5,
            maxLines = 15,
        )

        IconButton(
            onClick = {
//                println("PASTE CLICKED FOR PLAINTEXT INPUT!!! ")
                vm.doPastePlaintextFromClipboard(clipboardManager)
            }
        ) {
            Icon(
                Icons.Default.ContentPaste,
                contentDescription = anyResText(AnyRes(Res.string.paste_plaintext)),
            )
        }

    }
}
