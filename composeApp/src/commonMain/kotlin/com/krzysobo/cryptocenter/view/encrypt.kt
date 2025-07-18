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
import com.krzysobo.cryptocenter.appres.AppRes
import com.krzysobo.cryptocenter.getPlatform
import com.krzysobo.cryptocenter.view.common.encryptDecryptSwitcherButtons
import com.krzysobo.cryptocenter.view.encrypt.CiphertextOutputWidget
import com.krzysobo.cryptocenter.view.encrypt.KeySourceButtonsWidget
import com.krzysobo.cryptocenter.view.encrypt.PlainTextInputWidget
import com.krzysobo.cryptocenter.view.encrypt.SecretKeyHexWidget
import com.krzysobo.cryptocenter.viewmodel.EncryptPageVM
import com.krzysobo.cryptocenter.viewmodel.getEncryptPageVM
import com.krzysobo.soboapptpl.service.AnyRes
import com.krzysobo.soboapptpl.service.anyResText
import com.krzysobo.soboapptpl.widgets.ErrorMessageBox
import com.krzysobo.soboapptpl.widgets.PageHeaderTight
import com.krzysobo.soboapptpl.widgets.isPlatformAndroid
import kotlinx.coroutines.launch


@Composable
fun PageSoboCryptoCenterEncrypt() {
    val vm: EncryptPageVM = getEncryptPageVM()
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    vm.plaintext = remember { mutableStateOf(vm.plaintext.value) }
    vm.ciphertextHex = remember { mutableStateOf(vm.ciphertextHex.value) }

    vm.secretKeyBytes = remember { mutableStateOf(vm.secretKeyBytes.value) }
    vm.secretKeyHex = remember { mutableStateOf(vm.secretKeyHex.value) }
    vm.secretKeyHexSrcPass = remember { mutableStateOf(vm.secretKeyHexSrcPass.value) }

    vm.isErrorPlaintext = remember { mutableStateOf(vm.isErrorPlaintext.value) }
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
            encryptDecryptSwitcherButtons("encrypt")
        }

        item {
            PageHeaderTight(anyResText(AnyRes(AppRes.string.cryptocenter_encryption)))
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
            PlainTextInputWidget()
        }

        item {
            SecretKeyHexWidget()
        }

        item {
            if (!isPlatformAndroid(getPlatform().name)) {
                KeySourceButtonsWidget()
            }
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
            ) { Text(anyResText(AnyRes(AppRes.string.encrypt))) }
        }

        item {
            if (vm.isFormSent.value) {
                CiphertextOutputWidget()
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
