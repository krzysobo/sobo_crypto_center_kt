package com.krzysobo.cryptocenter.view.dh.decrypt

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
import com.krzysobo.cryptocenter.appres.AppRes
import com.krzysobo.cryptocenter.viewmodel.dh.DecryptDhPageVM
import com.krzysobo.cryptocenter.viewmodel.getDecryptDhPageVM
import com.krzysobo.soboapptpl.service.AnyRes
import com.krzysobo.soboapptpl.service.anyResText
import com.krzysobo.soboapptpl.widgets.PasswordWidget

@Composable
fun OurKeyPairHexWidgetForDecrypt() {
    val focusManager = LocalFocusManager.current
    val vm: DecryptDhPageVM = getDecryptDhPageVM()
    val clipboardManager: ClipboardManager = LocalClipboardManager.current

    // ====== our public key =====
    Row(
        modifier = Modifier
            .padding(all = 10.dp)
            .fillMaxWidth()
    ) {
        PasswordWidget(
            value = vm.ourPublicKeyHex.value,
            onValueChanges = { data: String ->
                vm.ourPublicKeyHex.value = data
                vm.clearOurPublicKeyHexError()
                vm.validateOurPublicKeyHex(vm.ourPublicKeyHex.value)
            },
            isError = vm.isErrorOurPublicKeyHex.value,
            focusManager = focusManager,
            labelText = anyResText(AnyRes(AppRes.string.our_public_key_hex)),
            placeHolderText = anyResText(AnyRes(AppRes.string.our_public_key_hex)),
            errorText = anyResText(AnyRes(AppRes.string.our_public_key_hex_required)),
            trailingIconPassOnClick = { vm.toggleOurPublicKeyHexVisible() },
            isPassVisible = vm.isOurPublicKeyHexVisible.value,
            isReadOnly = true,
            modifier = Modifier.weight(0.9f)
        )

        IconButton(
            enabled = (vm.ourPublicKeyHex.value != ""),
            onClick = {
                vm.doCopyOurPublicKeyHexToClipboard(clipboardManager)
            }
        ) {
            Icon(
                Icons.Default.ContentCopy,
                contentDescription = anyResText(AnyRes(AppRes.string.copy_hex_key)),
            )
        }

        IconButton(
            onClick = {
                vm.doPasteOurPublicKeyHexFromClipboard(clipboardManager)
            }
        ) {
            Icon(
                Icons.Default.ContentPaste,
                contentDescription = anyResText(AnyRes(AppRes.string.paste_hex_key)),
            )
        }
    }

    // ===== our private key =====
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
                vm.validateSecretKeyHex(vm.secretKeyHex.value)
            },
            isError = vm.isErrorSecretKeyHex.value,
            focusManager = focusManager,
            labelText = anyResText(AnyRes(AppRes.string.our_secret_key_hex)),
            placeHolderText = anyResText(AnyRes(AppRes.string.our_secret_key_hex)),
            errorText = anyResText(AnyRes(AppRes.string.our_secret_key_hex_required)),
            trailingIconPassOnClick = { vm.toggleSecretKeyHexVisible() },
            isPassVisible = vm.isSecretKeyHexVisible.value,
            isReadOnly = true,
            modifier = Modifier.weight(0.9f)
        )


        IconButton(
            enabled = (vm.secretKeyHex.value != ""),
            onClick = {
                vm.doCopySecretKeyHexToClipboard(clipboardManager)
            }
        ) {
            Icon(
                Icons.Default.ContentCopy,
                contentDescription = anyResText(AnyRes(AppRes.string.copy_hex_key)),
            )
        }

        IconButton(
            onClick = {
                vm.doPasteSecretKeyHexFromClipboard(clipboardManager)
            }
        ) {
            Icon(
                Icons.Default.ContentPaste,
                contentDescription = anyResText(AnyRes(AppRes.string.paste_hex_key)),
            )
        }
    }
}
