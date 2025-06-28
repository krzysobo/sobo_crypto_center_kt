package com.krzysobo.cryptocenter.viewmodel

import com.krzysobo.cryptocenter.viewmodel.dh.EncryptDhPageVM


fun getEncryptPageVM(): EncryptPageVM {
    return AllViewModels.encryptPageVM
}

fun getEncryptDhPageVM(): EncryptDhPageVM {
    return AllViewModels.encryptDhPageVM
}

fun getDecryptPageVM(): DecryptPageVM {
    return AllViewModels.decryptPageVM
}

fun getSettingsPageVM(): SettingsPageVM {
    return AllViewModels.settingsPageVM
}

private object AllViewModels {
    val encryptPageVM = EncryptPageVM()
    val encryptDhPageVM = EncryptDhPageVM()

    val decryptPageVM = DecryptPageVM()
    val settingsPageVM = SettingsPageVM()
}

