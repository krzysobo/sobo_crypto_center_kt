package com.krzysobo.cryptocenter.view.decrypt

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
import apptpl.composeapp.generated.resources.Res
import apptpl.composeapp.generated.resources.cipher_text_hex
import apptpl.composeapp.generated.resources.paste_ciphertext
import com.krzysobo.soboapptpl.service.AnyRes
import com.krzysobo.soboapptpl.service.anyResText
import com.krzysobo.soboapptpl.widgets.TextFieldWithErrorsKeyboardSettings
import com.krzysobo.cryptocenter.viewmodel.DecryptPageVM
import com.krzysobo.cryptocenter.viewmodel.getDecryptPageVM

@Composable
fun CiphertextHexInputWidget() {
    val focusManager = LocalFocusManager.current
    val vm: DecryptPageVM = getDecryptPageVM()
    val clipboardManager: ClipboardManager = LocalClipboardManager.current

    Row(modifier = Modifier.fillMaxWidth()) {
        TextFieldWithErrorsKeyboardSettings(
            value = vm.ciphertextHex.value,
            onValueChanges = { data: String ->
                vm.ciphertextHex.value = data
                vm.clearCiphertextHexError()
            },
            modifier = Modifier.padding(all = 10.dp).weight(0.9f),
            labelText = anyResText(AnyRes(Res.string.cipher_text_hex)),
            placeHolderText = anyResText(AnyRes(Res.string.cipher_text_hex)),
            leadingIcon = null,
            isError = vm.isErrorCiphertextHex.value,
            errorText = anyResText(AnyRes(Res.string.cipher_text_hex)),
            focusManager = focusManager,
            singleLine = false,
            minLines = 5,
            maxLines = 15,
        )

        IconButton(
            onClick = {
//                println("PASTE CLICKED FOR CIPHERTEXT HEX!!! ")
                vm.doPasteCiphertextHexFromClipboard(clipboardManager)
            }
        ) {
            Icon(
                Icons.Default.ContentPaste,
                contentDescription = anyResText(AnyRes(Res.string.paste_ciphertext)),
            )
        }
    }

}
