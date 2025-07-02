package com.krzysobo.cryptocenter.view.dh.encrypt

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
import com.krzysobo.cryptocenter.appres.AppRes
import com.krzysobo.cryptocenter.viewmodel.dh.EncryptDhPageVM
import com.krzysobo.cryptocenter.viewmodel.getEncryptDhPageVM
import com.krzysobo.soboapptpl.service.AnyRes
import com.krzysobo.soboapptpl.service.anyResText
import com.krzysobo.soboapptpl.widgets.TextFieldWithErrorsKeyboardSettings

@Composable
fun DhPlainTextInputWidget() {
    val focusManager = LocalFocusManager.current
    val vm: EncryptDhPageVM = getEncryptDhPageVM()
    val clipboardManager: ClipboardManager = LocalClipboardManager.current

    Row(modifier = Modifier.fillMaxWidth()) {
        TextFieldWithErrorsKeyboardSettings(
            value = vm.plaintext.value,
            onValueChanges = { data: String ->
                vm.plaintext.value = data
                vm.clearPlaintextError()
            },
            modifier = Modifier.padding(all = 10.dp).weight(0.9f),
            labelText = anyResText(AnyRes(AppRes.string.plaintext)),
            placeHolderText = anyResText(AnyRes(AppRes.string.plaintext)),
            leadingIcon = null,
            isError = vm.isErrorPlaintext.value,
            errorText = anyResText(AnyRes(AppRes.string.plaintext_required)),
            focusManager = focusManager,
            singleLine = false,
            minLines = 5,
            maxLines = 15,
        )

        IconButton(
            onClick = {
                vm.doPastePlaintextFromClipboard(clipboardManager)
            }
        ) {
            Icon(
                Icons.Default.ContentPaste,
                contentDescription = anyResText(AnyRes(AppRes.string.paste_plaintext)),
            )
        }
    }
}
