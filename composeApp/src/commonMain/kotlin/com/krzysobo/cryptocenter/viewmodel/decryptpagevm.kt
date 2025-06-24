package com.krzysobo.cryptocenter.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.text.buildAnnotatedString
import com.krzysobo.soboapptpl.viewmodel.SoboViewModel
import com.krzysobo.soboapptpl.widgets.strNotEmpty
import com.krzysobo.sobocryptolib.crypto.service.CryptoService

class DecryptPageVM : SoboViewModel() {
    var ciphertextHex: MutableState<String> = mutableStateOf("")
    var plaintext: MutableState<String> = mutableStateOf("")

    // ---- secret key hex -----
    var secretKeyHex: MutableState<String> = mutableStateOf("")
    var secretKeyBytes: MutableState<ByteArray> = mutableStateOf(byteArrayOf())
    var isSecretKeyHexVisible: MutableState<Boolean> = mutableStateOf(false)
    // ---- /secret key hex -----

    // ---- secret key hex password ----
    var secretKeyHexSrcPass: MutableState<String> = mutableStateOf("")
    var isSecretKeyHexSrcPassVisible: MutableState<Boolean> = mutableStateOf(false)
    var isSecretKeyHexSrcPassWidgetOpen: MutableState<Boolean> = mutableStateOf(false)
    // ---- /secret key hex password ----


    var isErrorCiphertextHex: MutableState<Boolean> = mutableStateOf(false)
    var isErrorSecretKeyHex: MutableState<Boolean> = mutableStateOf(false)
    var isErrorSecretKeyHexSrcPass: MutableState<Boolean> = mutableStateOf(false)

    // 14A6A81559B21B1922A7FF49B39009875AD543463EB5F92795532023590D1B28
    @OptIn(ExperimentalStdlibApi::class)
    fun doPasteKeyHexFromClipboard(clipboardManager: ClipboardManager) {
        clipboardManager.getText()?.text?.let {
            if (validateSecretKeyHexStr(it)) {
                secretKeyHex.value = it
                secretKeyBytes.value = CryptoService().hexToBytes(it)
            }
        }
    }

    fun doPasteCiphertextHexFromClipboard(clipboardManager: ClipboardManager) {
        clipboardManager.getText()?.text?.let {
            if (it != "") {
                ciphertextHex.value = it
            }
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun doCopyKeyHexToClipboard(clipboardManager: ClipboardManager) {
        println("COPYING HEX KEY TO CLIPBOARD -- 00 -- ${secretKeyHex.value}")
        if (validateSecretKeyHex()) {
            println("COPYING HEX KEY TO CLIPBOARD -- 01 - OK -- ${secretKeyHex.value}")
            clipboardManager.setText(buildAnnotatedString { append(secretKeyHex.value) })
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun doCopyPlaintextToClipboard(clipboardManager: ClipboardManager) {
        println("COPYING PLAINTEXT HEX TO CLIPBOARD -- 00 -- ${plaintext.value}")
        clipboardManager.setText(buildAnnotatedString { append(plaintext.value) })
    }

    fun doClearAll() {
        ciphertextHex.value = ""
        plaintext.value = ""
        secretKeyHex.value = ""
        secretKeyBytes.value = byteArrayOf()
        isSecretKeyHexVisible.value = false
        secretKeyHexSrcPass.value = ""
        isSecretKeyHexSrcPassVisible.value = false
        isSecretKeyHexSrcPassWidgetOpen.value = false

        clearErrors()
    }

    @OptIn(ExperimentalStdlibApi::class)
    suspend fun doDecrypt(): Boolean {
        println("\nDecrypting the text '${plaintext.value}'.... \n")
        try {
            val valRes = validateSecretKeyHex()
            if (!valRes) {
                println("\nSECRET KEY HEX ERROR!!!! \n")
                isFormSent.value = false
                isApiError.value = true
                apiErrorDetails.value = "Validation has failed. Check your data inputs."
                return false
            }

            println("---------> doDecrypt-11111")

            val plaintextBytes = CryptoService().aesGcmDecryptFromPortableHex(
                ciphertextHex = ciphertextHex.value,
                aesKeyHex = secretKeyHex.value,
            )

            plaintext.value = plaintextBytes.decodeToString()

            println("---------> doDecrypt-22222  ${ciphertextHex.value}")

            isFormSent.value = true
            isApiError.value = false
            apiErrorDetails.value = ""

            return true
        } catch (e: Exception) {
            println("----> doEncrypt ERROR: ${e.message} ")
            isApiError.value = true
            apiErrorDetails.value = "${e.message}"
        }

        return false
    }

    fun validateSecretKeyHex(): Boolean {
        val res = CryptoService().isAesKeyHexValid(secretKeyHex.value)
        isErrorSecretKeyHex.value = !res
        return res
    }

    fun validateSecretKeyHexStr(key: String): Boolean {
        val res = CryptoService().isAesKeyHexValid(key)
        isErrorSecretKeyHex.value = !res
        return res
    }

    fun validateSecretKeyHexSrcPass(): Boolean {
        val res = strNotEmpty(secretKeyHexSrcPass.value)
        isErrorSecretKeyHexSrcPass.value = !res
        return res
    }

    fun validateCiphertextHex(): Boolean {
        val res = strNotEmpty(ciphertextHex.value)
        isErrorCiphertextHex.value = !res
        return res
    }

    fun validate(): Boolean {
        clearErrors()
        val resCiphertextHex = validateCiphertextHex()
        val resSecretKeyHex = validateSecretKeyHex()
        println("\nDecryptPageVM - validate CIPHERTEXT: $resCiphertextHex KEY: $resSecretKeyHex \n")
        var res = resCiphertextHex && resSecretKeyHex

        if (isSecretKeyHexSrcPassWidgetOpen.value) {
            res = res && validateSecretKeyHexSrcPass()
            println("\nDecryptPageVM - validate PASSWORD : ${validateSecretKeyHexSrcPass()} ")
        }

        println("\nDecryptPageVM - validate CIPHERTEXT: $resCiphertextHex KEY: $resSecretKeyHex RES ALL: $res\n")
        return res
    }

    fun clearCiphertextHexError() {
        clearErrorFlag(isErrorCiphertextHex)
    }

    fun clearSecretKeyHexError() {
        clearErrorFlag(isErrorSecretKeyHex)
    }

    fun clearSecretKeyHexSrcPassError() {
        clearErrorFlag(isErrorSecretKeyHexSrcPass)
    }

    fun clearErrors() {
        clearCiphertextHexError()
        clearSecretKeyHexError()
        clearSecretKeyHexSrcPassError()

        clearApiError()
        clearFormSent()
    }

    fun toggleSecretKeyHexVisible() {
        isSecretKeyHexVisible.value = !isSecretKeyHexVisible.value
    }

    fun toggleSecretKeyHexSrcPassVisible() {
        isSecretKeyHexSrcPassVisible.value = !isSecretKeyHexSrcPassVisible.value
    }

    fun openSecretKeyHexSrcPassWidget() {
        isSecretKeyHexSrcPassWidgetOpen.value = true
    }

    fun closeSecretKeyHexSrcPassWidget() {
        isSecretKeyHexSrcPassWidgetOpen.value = false
    }

}
