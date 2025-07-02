package com.krzysobo.cryptocenter.view.dh.decrypt

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
import com.krzysobo.cryptocenter.viewmodel.dh.DecryptDhPageVM
import com.krzysobo.cryptocenter.viewmodel.getDecryptDhPageVM
import com.krzysobo.soboapptpl.service.AnyRes
import com.krzysobo.soboapptpl.service.anyResText
import com.krzysobo.soboapptpl.widgets.TextFieldWithErrorsKeyboardSettings

@Composable
fun DhPlainTextOutputWidget() {
    val focusManager = LocalFocusManager.current
    val vm: DecryptDhPageVM = getDecryptDhPageVM()
    val clipboardManager: ClipboardManager = LocalClipboardManager.current

    Row(modifier = Modifier.fillMaxWidth()) {
        TextFieldWithErrorsKeyboardSettings(
            value = vm.plaintext.value,
            onValueChanges = {},
//            onValueChanges = { data: String ->
//                vm.plaintext.value = data
//
//            },
            modifier = Modifier.padding(all = 10.dp).weight(0.9f),
            labelText = anyResText(AnyRes(AppRes.string.plaintext)),
            placeHolderText = anyResText(AnyRes(AppRes.string.plaintext)),
            leadingIcon = null,
            readOnly = true,
            isError = false,
            errorText = "",
            focusManager = focusManager,
            singleLine = false,
            minLines = 5,
            maxLines = 15,
        )

        IconButton(
            onClick = {
                vm.doCopyPlaintextToClipboard(clipboardManager)
            }
        ) {
            Icon(
                Icons.Default.ContentCopy,
                contentDescription = anyResText(AnyRes(AppRes.string.copy)),
            )
        }
    }
}
