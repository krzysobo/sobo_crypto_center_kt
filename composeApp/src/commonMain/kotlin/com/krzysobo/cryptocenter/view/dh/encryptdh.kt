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
import com.krzysobo.cryptocenter.appres.AppRes
import com.krzysobo.cryptocenter.getPlatform
import com.krzysobo.cryptocenter.view.dh.encrypt.DhCiphertextOutputWidget
import com.krzysobo.cryptocenter.view.dh.encrypt.DhPlainTextInputWidget
import com.krzysobo.cryptocenter.view.dh.encrypt.OurKeyPairHexWidget
import com.krzysobo.cryptocenter.view.dh.encrypt.OurKeyPairSourceButtonsWidget
import com.krzysobo.cryptocenter.view.dh.encrypt.TheirPublicKeyHexWidget
import com.krzysobo.cryptocenter.viewmodel.dh.EncryptDhPageVM
import com.krzysobo.cryptocenter.viewmodel.getEncryptDhPageVM
import com.krzysobo.soboapptpl.service.AnyRes
import com.krzysobo.soboapptpl.service.anyResText
import com.krzysobo.soboapptpl.widgets.ErrorMessageBox
import com.krzysobo.soboapptpl.widgets.PageHeaderTight
import com.krzysobo.soboapptpl.widgets.isPlatformAndroid
import kotlinx.coroutines.launch


@Composable
fun PageSoboCryptoCenterEncryptDh() {
    val vm: EncryptDhPageVM = getEncryptDhPageVM()
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

    vm.isErrorPlaintext = remember { mutableStateOf(vm.isErrorPlaintext.value) }

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
            DhEncryptDecryptSwitcherButtons("encrypt")
        }

        item {
            PageHeaderTight(anyResText(AnyRes(AppRes.string.cryptocenter_encryption_dh)))
        }

        item {
            if (vm.isApiError.value) {
                val errorText = if (vm.apiErrorDetails.value != "") vm.apiErrorDetails.value else
                    anyResText(AnyRes(AppRes.string.error_unknown_error))
                ErrorMessageBox(
                    "* ${anyResText(AnyRes(AppRes.string.encryption_error_header))} *",
                    errorText
                )
            }
        }

        item {
            DhPlainTextInputWidget()
        }

        item {
            OurKeyPairHexWidget()
        }

        item {
            if (!isPlatformAndroid(getPlatform().name)) {
                OurKeyPairSourceButtonsWidget()
            }
        }

        item {
            TheirPublicKeyHexWidget()
        }

        item {
            Button(
                onClick = {
                    vm.clearErrors()
                    val resForm: Boolean = vm.validate()
                    if (resForm) {
                        coroutineScope.launch {
                            vm.doEncrypt()
                        }
                    }
                },
                modifier = Modifier.padding(all = 10.dp).fillMaxWidth()
            ) { Text(anyResText(AnyRes(AppRes.string.encrypt_dh))) }
        }

        item {
            if (vm.isFormSent.value) {
                DhCiphertextOutputWidget()
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
            ) { Text(anyResText(AnyRes(AppRes.string.clear_all))) }
        }

    }
}
