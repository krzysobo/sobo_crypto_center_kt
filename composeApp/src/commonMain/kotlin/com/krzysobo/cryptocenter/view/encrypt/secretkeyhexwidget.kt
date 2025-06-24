package com.krzysobo.cryptocenter.view.encrypt

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.ContentPaste
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Key
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import sobocryptocenter.composeapp.generated.resources.Res
import sobocryptocenter.composeapp.generated.resources.copy_hex_key
import sobocryptocenter.composeapp.generated.resources.generate_random
import sobocryptocenter.composeapp.generated.resources.paste_hex_key
import sobocryptocenter.composeapp.generated.resources.secret_key_hex
import sobocryptocenter.composeapp.generated.resources.secret_key_hex_required
import com.krzysobo.soboapptpl.service.AnyRes
import com.krzysobo.soboapptpl.service.anyResText
import com.krzysobo.soboapptpl.widgets.PasswordWidget
import com.krzysobo.cryptocenter.viewmodel.EncryptPageVM
import com.krzysobo.cryptocenter.viewmodel.getEncryptPageVM

@Composable
fun SecretKeyHexWidget() {
    val focusManager = LocalFocusManager.current
    val vm: EncryptPageVM = getEncryptPageVM()
    val clipboardManager: ClipboardManager = LocalClipboardManager.current

    Row(
        modifier = Modifier
            .padding(all = 10.dp)
            .fillMaxWidth()
    ) {
        PasswordWidget(
            value = vm.secretKeyHex.value,
            onValueChanges = { data: String ->
                vm.secretKeyHex.value = data
                vm.clearSecretKeyHexError()
                vm.validateSecretKeyHex()
            },
            isError = vm.isErrorSecretKeyHex.value,
            focusManager = focusManager,
            labelText = anyResText(AnyRes(Res.string.secret_key_hex)),
            placeHolderText = anyResText(AnyRes(Res.string.secret_key_hex)),
            errorText = anyResText(AnyRes(Res.string.secret_key_hex_required)),
            trailingIconPassOnClick = { vm.toggleSecretKeyHexVisible() },
            isPassVisible = vm.isSecretKeyHexVisible.value,
            isReadOnly = true,
            modifier = Modifier.weight(0.9f)
        )

        IconButton(
            enabled = (vm.secretKeyHex.value != ""),
            onClick = {
//                println("COPY CLICKED FOR HEX KEY!!! ")
                vm.doCopyKeyHexToClipboard(clipboardManager)
            }
        ) {
            Icon(
                Icons.Default.ContentCopy,
                contentDescription = anyResText(AnyRes(Res.string.copy_hex_key)),
            )
        }

        IconButton(
            onClick = {
//                println("PASTE CLICKED FOR HEX KEY!!! ")
                vm.doPasteKeyHexFromClipboard(clipboardManager)
            }
        ) {
            Icon(
                Icons.Default.ContentPaste,
                contentDescription = anyResText(AnyRes(Res.string.paste_hex_key)),
            )
        }

        IconButton(
            onClick = {
                vm.doGenerateRandomKey()
            }
        ) {
            Icon(
                Icons.Default.Key,
                contentDescription = anyResText(AnyRes(Res.string.generate_random)),
            )
        }

    }
}
