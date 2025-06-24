package com.krzysobo.cryptocenter.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.krzysobo.soboapptpl.viewmodel.LangSettingsVM
import com.krzysobo.soboapptpl.viewmodel.SoboViewModel


class SettingsPageVM : SoboViewModel(
) {
    val langSettings = LangSettingsVM()
    var isSettingsUpToDate: MutableState<Boolean> = mutableStateOf(false)

    fun doRefreshSettingsFromAppSettings() {
        langSettings.doRefreshSettingsFromAppSettings()
    }

    fun doUpdateAppSettings(): Boolean {
        val res = langSettings.doUpdateAppSettings()
        isFormSent.value = langSettings.isFormSent.value
        isApiError.value = langSettings.isApiError.value
        apiErrorDetails.value = langSettings.apiErrorDetails.value

        return res
    }

    fun clearErrors() {}

    fun validate(): Boolean {
        clearErrors()

        val res = langSettings.validate()
        return res
    }

}
