package com.krzysobo.cryptocenter.view.dh

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.krzysobo.cryptocenter.getPlatform
import com.krzysobo.cryptocenter.view.common.encryptDecryptSwitcherButtons
import com.krzysobo.cryptocenter.view.dh.decrypt.DhCiphertextInputWidget
import com.krzysobo.cryptocenter.view.dh.decrypt.DhPlainTextOutputWidget
import com.krzysobo.cryptocenter.view.dh.decrypt.OurKeyPairHexWidgetForDecrypt
import com.krzysobo.cryptocenter.view.dh.decrypt.OurKeyPairSourceButtonsWidgetForDecrypt
import com.krzysobo.cryptocenter.view.dh.decrypt.TheirPublicKeyHexWidgetForDecrypt
import com.krzysobo.cryptocenter.view.dh.encrypt.DhCiphertextOutputWidget
import com.krzysobo.cryptocenter.view.dh.encrypt.DhPlainTextInputWidget
import com.krzysobo.cryptocenter.view.dh.encrypt.OurKeyPairHexWidget
import com.krzysobo.cryptocenter.view.dh.encrypt.OurKeyPairSourceButtonsWidget
import com.krzysobo.cryptocenter.view.dh.encrypt.TheirPublicKeyHexWidget
import com.krzysobo.cryptocenter.view.encrypt.KeySourceButtonsWidget
import com.krzysobo.cryptocenter.viewmodel.dh.DecryptDhPageVM
import com.krzysobo.cryptocenter.viewmodel.dh.EncryptDhPageVM
import com.krzysobo.cryptocenter.viewmodel.getDecryptDhPageVM
import com.krzysobo.cryptocenter.viewmodel.getEncryptDhPageVM
import com.krzysobo.soboapptpl.service.AnyRes
import com.krzysobo.soboapptpl.service.anyResText
import com.krzysobo.soboapptpl.widgets.ErrorMessageBox
import com.krzysobo.soboapptpl.widgets.PageHeaderTight
import com.krzysobo.soboapptpl.widgets.isPlatformAndroid
import kotlinx.coroutines.launch
import sobocryptocenter.composeapp.generated.resources.Res
import sobocryptocenter.composeapp.generated.resources.clear_all
import sobocryptocenter.composeapp.generated.resources.cryptocenter_decryption_dh
import sobocryptocenter.composeapp.generated.resources.cryptocenter_encryption
import sobocryptocenter.composeapp.generated.resources.cryptocenter_encryption_dh
import sobocryptocenter.composeapp.generated.resources.decrypt_dh
import sobocryptocenter.composeapp.generated.resources.decryption_error_header
import sobocryptocenter.composeapp.generated.resources.encrypt
import sobocryptocenter.composeapp.generated.resources.encrypt_dh
import sobocryptocenter.composeapp.generated.resources.encryption_error_header
import sobocryptocenter.composeapp.generated.resources.error_unknown_error


@Composable
fun PageSoboCryptoCenterDecryptDh() {
    val vm: DecryptDhPageVM = getDecryptDhPageVM()
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    vm.plaintext = remember { mutableStateOf(vm.plaintext.value) }
    vm.ciphertextHex = remember { mutableStateOf(vm.ciphertextHex.value) }

    vm.secretKeyBytes = remember { mutableStateOf(vm.secretKeyBytes.value) }
    vm.secretKeyHex = remember { mutableStateOf(vm.secretKeyHex.value) }

    vm.ourPublicKeyBytes = remember { mutableStateOf(vm.ourPublicKeyBytes.value) }
    vm.ourPublicKeyHex = remember { mutableStateOf(vm.ourPublicKeyHex.value) }

    vm.theirPublicKeyBytes = remember { mutableStateOf(vm.theirPublicKeyBytes.value) }
    vm.theirPublicKeyHex = remember { mutableStateOf(vm.theirPublicKeyHex.value) }

    vm.isErrorCiphertextHex = remember { mutableStateOf(vm.isErrorCiphertextHex.value) }

    vm.isErrorSecretKeyHex = remember { mutableStateOf(vm.isErrorSecretKeyHex.value) }
    vm.isErrorOurPublicKeyHex = remember { mutableStateOf(vm.isErrorOurPublicKeyHex.value) }
    vm.isErrorTheirPublicKeyHex = remember { mutableStateOf(vm.isErrorTheirPublicKeyHex.value) }

    vm.isSecretKeyHexVisible = remember { mutableStateOf(vm.isSecretKeyHexVisible.value) }
    vm.isOurPublicKeyHexVisible = remember { mutableStateOf(vm.isOurPublicKeyHexVisible.value) }
    vm.isTheirPublicKeyHexVisible = remember { mutableStateOf(vm.isTheirPublicKeyHexVisible.value) }

    LazyColumn(
        modifier = Modifier
//            .border(BorderStroke(width = 1.dp, color = Color.Blue))
            .padding(top = 0.dp, bottom = 0.dp)
    ) {
        item {
            DhEncryptDecryptSwitcherButtons("decrypt")
        }

        item {
            PageHeaderTight(anyResText(AnyRes(Res.string.cryptocenter_decryption_dh)))
        }

        item {
            if (vm.isApiError.value) {
                val errorText = if (vm.apiErrorDetails.value != "") vm.apiErrorDetails.value else
                    anyResText(AnyRes(Res.string.error_unknown_error))
                ErrorMessageBox(
                    "* ${anyResText(AnyRes(Res.string.decryption_error_header))} *",
                    errorText
                )
            }
        }

        item {
            DhCiphertextInputWidget()
        }

        item {
            OurKeyPairHexWidgetForDecrypt()
        }

        item {
            if (!isPlatformAndroid(getPlatform().name)) {
                OurKeyPairSourceButtonsWidgetForDecrypt()
            }
        }

        item {
            TheirPublicKeyHexWidgetForDecrypt()
        }

        item {
            Button(
                onClick = {
                    vm.clearErrors()
                    val resForm: Boolean = vm.validate()
                    if (resForm) {
                        coroutineScope.launch {
                            vm.doDecrypt()
                        }
                    }
                },
                modifier = Modifier.padding(all = 10.dp).fillMaxWidth()
            ) { Text(anyResText(AnyRes(Res.string.decrypt_dh))) }
        }

        item {
            if (vm.isFormSent.value) {
                DhPlainTextOutputWidget()
            }
        }

        item {
            Button(
                onClick = {
                    coroutineScope.launch {
                        vm.doClearAll()
                    }
                },
                modifier = Modifier.padding(all = 10.dp).fillMaxWidth()
            ) { Text(anyResText(AnyRes(Res.string.clear_all))) }
        }

    }

}
