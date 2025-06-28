package com.krzysobo.cryptocenter.view.dh.encrypt

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.ContentPaste
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.krzysobo.cryptocenter.viewmodel.dh.EncryptDhPageVM
import com.krzysobo.cryptocenter.viewmodel.getEncryptDhPageVM
import com.krzysobo.soboapptpl.service.AnyRes
import com.krzysobo.soboapptpl.service.anyResText
import com.krzysobo.soboapptpl.widgets.PasswordWidget
import sobocryptocenter.composeapp.generated.resources.Res
import sobocryptocenter.composeapp.generated.resources.copy_hex_key
import sobocryptocenter.composeapp.generated.resources.paste_hex_key
import sobocryptocenter.composeapp.generated.resources.their_public_key_hex
import sobocryptocenter.composeapp.generated.resources.their_public_key_hex_required

@Composable
fun TheirPublicKeyHexWidget() {
    val focusManager = LocalFocusManager.current
    val vm: EncryptDhPageVM = getEncryptDhPageVM()
    val clipboardManager: ClipboardManager = LocalClipboardManager.current

    Row(
        modifier = Modifier
            .padding(all = 10.dp)
            .fillMaxWidth()
    ) {
        PasswordWidget(
            value = vm.theirPublicKeyHex.value,
            onValueChanges = { data: String ->
                vm.theirPublicKeyHex.value = data
                vm.clearTheirPublicKeyHexError()
                vm.validateTheirPublicKeyHex(vm.theirPublicKeyHex.value)
            },
            isError = vm.isErrorTheirPublicKeyHex.value,
            focusManager = focusManager,
            labelText = anyResText(AnyRes(Res.string.their_public_key_hex)),
            placeHolderText = anyResText(AnyRes(Res.string.their_public_key_hex)),
            errorText = anyResText(AnyRes(Res.string.their_public_key_hex_required)),
            trailingIconPassOnClick = { vm.toggleTheirPublicKeyHexVisible() },
            isPassVisible = vm.isTheirPublicKeyHexVisible.value,
            isReadOnly = true,
            modifier = Modifier.weight(0.9f)
        )

        IconButton(
            enabled = (vm.theirPublicKeyHex.value != ""),
            onClick = {
                vm.doCopyTheirPublicKeyHexToClipboard(clipboardManager)
            }
        ) {
            Icon(
                Icons.Default.ContentCopy,
                contentDescription = anyResText(AnyRes(Res.string.copy_hex_key)),
            )
        }

        IconButton(
            onClick = {
                vm.doPasteTheirPublicKeyHexFromClipboard(clipboardManager)
            }
        ) {
            Icon(
                Icons.Default.ContentPaste,
                contentDescription = anyResText(AnyRes(Res.string.paste_hex_key)),
            )
        }
    }
}
