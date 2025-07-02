package com.krzysobo.cryptocenter.view.decrypt

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
import com.krzysobo.cryptocenter.viewmodel.DecryptPageVM
import com.krzysobo.cryptocenter.viewmodel.getDecryptPageVM
import com.krzysobo.soboapptpl.service.AnyRes
import com.krzysobo.soboapptpl.service.anyResText
import com.krzysobo.soboapptpl.widgets.TextFieldWithErrorsKeyboardSettings

@Composable
fun PlaintextOutputWidget() {
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    val vm: DecryptPageVM = getDecryptPageVM()
    val focusManager = LocalFocusManager.current

    Row(modifier = Modifier.fillMaxWidth()) {
        TextFieldWithErrorsKeyboardSettings(
            value = vm.plaintext.value,
            onValueChanges = { },
            readOnly = true,
            modifier = Modifier.padding(all = 10.dp).weight(0.9f),
            labelText = anyResText(AnyRes(AppRes.string.plaintext)),
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
            enabled = (vm.plaintext.value != ""),
            onClick = {
//                println("COPY CLICKED FOR PLAINTEXT!!! ")
                vm.doCopyPlaintextToClipboard(clipboardManager)
            }
        ) {
            Icon(
                Icons.Default.ContentCopy,
                contentDescription = anyResText(AnyRes(AppRes.string.copy_result_plaintext)),
            )
        }
    }
}
