package com.krzysobo.cryptocenter.view

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
import apptpl.composeapp.generated.resources.Res
import apptpl.composeapp.generated.resources.clear_all
import apptpl.composeapp.generated.resources.cryptocenter_decryption
import apptpl.composeapp.generated.resources.decrypt
import apptpl.composeapp.generated.resources.decryption_error_header
import apptpl.composeapp.generated.resources.error_unknown_error
import com.krzysobo.cryptocenter.getPlatform
import com.krzysobo.cryptocenter.view.common.encryptDecryptSwitcherButtons
import com.krzysobo.cryptocenter.view.decrypt.CiphertextHexInputWidget
import com.krzysobo.cryptocenter.view.decrypt.KeySourceButtonsForDecrypt
import com.krzysobo.cryptocenter.view.decrypt.PlaintextOutputWidget
import com.krzysobo.cryptocenter.view.decrypt.SecretKeyHexSrcPassWidget
import com.krzysobo.cryptocenter.view.decrypt.SecretKeyHexWidget
import com.krzysobo.cryptocenter.viewmodel.DecryptPageVM
import com.krzysobo.cryptocenter.viewmodel.getDecryptPageVM
import com.krzysobo.soboapptpl.service.AnyRes
import com.krzysobo.soboapptpl.service.anyResText
import com.krzysobo.soboapptpl.widgets.ErrorMessageBox
import com.krzysobo.soboapptpl.widgets.PageHeader
import com.krzysobo.soboapptpl.widgets.isPlatformAndroid
import kotlinx.coroutines.launch


@Composable
fun PageSoboCryptoCenterDecrypt() {
    val vm: DecryptPageVM = getDecryptPageVM()
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    vm.plaintext = remember { mutableStateOf(vm.plaintext.value) }
    vm.ciphertextHex = remember { mutableStateOf(vm.ciphertextHex.value) }

    vm.secretKeyBytes = remember { mutableStateOf(vm.secretKeyBytes.value) }
    vm.secretKeyHex = remember { mutableStateOf(vm.secretKeyHex.value) }
    vm.secretKeyHexSrcPass = remember { mutableStateOf(vm.secretKeyHexSrcPass.value) }

    vm.isErrorCiphertextHex = remember { mutableStateOf(vm.isErrorCiphertextHex.value) }
    vm.isErrorSecretKeyHex = remember { mutableStateOf(vm.isErrorSecretKeyHex.value) }
    vm.isErrorSecretKeyHexSrcPass = remember { mutableStateOf(vm.isErrorSecretKeyHexSrcPass.value) }

    vm.isSecretKeyHexVisible = remember { mutableStateOf(vm.isSecretKeyHexVisible.value) }
    vm.isSecretKeyHexSrcPassVisible =
        remember { mutableStateOf(vm.isSecretKeyHexSrcPassVisible.value) }

    vm.isSecretKeyHexSrcPassWidgetOpen =
        remember { mutableStateOf(vm.isSecretKeyHexSrcPassWidgetOpen.value) }



    LazyColumn(
        modifier = Modifier
//            .border(BorderStroke(width = 1.dp, color = Color.Blue))
            .padding(top = 0.dp, bottom = 0.dp)
    ) {
        item {
            encryptDecryptSwitcherButtons("decrypt")
        }

        item {
            PageHeader(anyResText(AnyRes(Res.string.cryptocenter_decryption)))
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
            CiphertextHexInputWidget()
        }

        item {
            SecretKeyHexWidget()
        }

        item {
            if (!isPlatformAndroid(getPlatform().name)) {
                KeySourceButtonsForDecrypt()
            }
        }

        item {
            SecretKeyHexSrcPassWidget()
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
            ) { Text(anyResText(AnyRes(Res.string.decrypt))) }
        }

        item {
            if (vm.isFormSent.value) {
                PlaintextOutputWidget()
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
