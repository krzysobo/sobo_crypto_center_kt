package com.krzysobo.cryptocenter.view

import WaitingSpinner
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.krzysobo.cryptocenter.appres.AppRes
import com.krzysobo.cryptocenter.settings.langListCryptoCenter
import com.krzysobo.cryptocenter.viewmodel.SettingsPageVM
import com.krzysobo.cryptocenter.viewmodel.getSettingsPageVM
import com.krzysobo.soboapptpl.service.AnyRes
import com.krzysobo.soboapptpl.service.anyResText
import com.krzysobo.soboapptpl.viewmodel.AppViewModelVM
import com.krzysobo.soboapptpl.widgets.ErrorMessageBox
import com.krzysobo.soboapptpl.widgets.MessageBox
import com.krzysobo.soboapptpl.widgets.PageHeader
import com.krzysobo.soboapptpl.widgets.SettingSelectLanguage
import com.krzysobo.soboapptpl.widgets.SettingUseSystemLang
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun PageSoboCryptoCenterSettings() {
    val vm: SettingsPageVM = getSettingsPageVM()
    val coroutineScope = rememberCoroutineScope()

    vm.langSettings.lang = remember { mutableStateOf("") }
    vm.langSettings.useSystemLang = remember { mutableStateOf(false) }
    vm.langSettings.isErrorLang = remember { mutableStateOf(false) }

    vm.isFormSent = remember { mutableStateOf(false) }
    vm.isSettingsUpToDate = remember { mutableStateOf(false) }

    vm.isApiError = remember { mutableStateOf(false) }
    vm.apiErrorDetails = remember { mutableStateOf("") }


    if (!vm.isSettingsUpToDate.value) {
        vm.doRefreshSettingsFromAppSettings()
        vm.isSettingsUpToDate.value = true
    }

//    Column(
//        Modifier
//            .fillMaxWidth()
//            .padding(all = 32.dp)
//    ) {
//
//    }
    var showColumn = remember { mutableStateOf(true) }
    if (showColumn.value) {

        LazyColumn(
            modifier = Modifier
                .padding(all = 10.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            item {
                PageHeader(
                    anyResText(
                        AnyRes(
                            AppRes.string.s_settings,
                            arrayOf("Sobo Crypto Center")
                        )
                    )
                )
            }

            item {
                if (vm.isFormSent.value) {
                    MessageBox(
                        "* ${anyResText(AnyRes(AppRes.string.settings_updated_ok))} *",
                        anyResText(AnyRes(AppRes.string.settings_updated_ok_desc))
                    )
                } else if (vm.isApiError.value) {
                    ErrorMessageBox(
                        "* ${anyResText(AnyRes(AppRes.string.settings_updating_error))} *",
                        vm.apiErrorDetails.value
                    )
                }
            }

            item {
                val focusManager = LocalFocusManager.current

                SettingUseSystemLang(vm.langSettings.useSystemLang)
                if (!vm.langSettings.useSystemLang.value) {
                    SettingSelectLanguage(langListCryptoCenter, vm.langSettings.lang)
                }
            }

            item {

                Button(
                    onClick = {
                        vm.clearErrors()
                        val resForm: Boolean = vm.validate()
                        if (resForm) {
                            coroutineScope.launch {
                                val res = vm.doUpdateAppSettings()
                                if (res) {
                                    vm.doRefreshSettingsFromAppSettings()
                                    coroutineScope.launch {
                                        showColumn.value = false
                                        AppViewModelVM.hideMenu()
                                        delay(700)
                                        showColumn.value = true
                                        delay(100)
                                        AppViewModelVM.showMenu()
                                    }
                                }
                            }
                        }
                    },
                    modifier = Modifier.padding(all = 10.dp)
                ) { Text(anyResText(AnyRes(AppRes.string.update_settings))) }
            }
        }
    } else {
        WaitingSpinner()
    }
}
