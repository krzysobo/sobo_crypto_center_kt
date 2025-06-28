package com.krzysobo.cryptocenter.viewmodel

import com.krzysobo.cryptocenter.viewmodel.dh.DecryptDhPageVM
import com.krzysobo.cryptocenter.viewmodel.dh.EncryptDhPageVM


fun getEncryptPageVM(): EncryptPageVM {
    return AllViewModels.encryptPageVM
}

fun getEncryptDhPageVM(): EncryptDhPageVM {
    return AllViewModels.encryptDhPageVM
}

fun getDecryptDhPageVM(): DecryptDhPageVM {
    return AllViewModels.decryptDhPageVM
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

    val encryptDhPageVM = EncryptDhPageVM()
    val decryptDhPageVM = DecryptDhPageVM()

    val settingsPageVM = SettingsPageVM()
}

