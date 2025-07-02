package com.krzysobo.cryptocenter.view.dh.encrypt

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.krzysobo.cryptocenter.appres.AppRes
import com.krzysobo.cryptocenter.viewmodel.dh.EncryptDhPageVM
import com.krzysobo.cryptocenter.viewmodel.getEncryptDhPageVM
import com.krzysobo.soboapptpl.service.AnyRes
import com.krzysobo.soboapptpl.service.anyResText
import com.krzysobo.soboapptpl.widgets.TextFieldWithErrorsKeyboardSettings

@Composable
fun DhCiphertextOutputWidget() {
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    val vm: EncryptDhPageVM = getEncryptDhPageVM()
    val focusManager = LocalFocusManager.current

    Row(modifier = Modifier.fillMaxWidth()) {
        TextFieldWithErrorsKeyboardSettings(
            value = vm.ciphertextHex.value,
            onValueChanges = { },
            readOnly = true,
            modifier = Modifier.padding(all = 10.dp).weight(0.9f),
            labelText = anyResText(AnyRes(AppRes.string.cipher_text_hex)),
            placeHolderText = "",
            leadingIcon = null,
            isError = false,
            errorText = "",
            focusManager = focusManager,
            singleLine = false,
            minLines = 5,
            maxLines = 15,
        )
        IconButton(
            onClick = {
                vm.doCopyCiphertextToClipboard(clipboardManager)
            }
        ) {
            Icon(
                Icons.Default.ContentCopy,
                contentDescription = anyResText(AnyRes(AppRes.string.copy_result_ciphertext))
            )
        }
    }
}
