package com.krzysobo.cryptocenter.viewmodel


fun getEncryptPageVM(): EncryptPageVM {
    return AllViewModels.encryptPageVM
}

fun getDecryptPageVM(): DecryptPageVM {
    return AllViewModels.decryptPageVM
}

fun getSettingsPageVM(): SettingsPageVM {
    return AllViewModels.settingsPageVM
}

private object AllViewModels {
    val encryptPageVM = EncryptPageVM()
    val decryptPageVM = DecryptPageVM()
    val settingsPageVM = SettingsPageVM()
}

