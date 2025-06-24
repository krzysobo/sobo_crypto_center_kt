package com.krzysobo.cryptocenter.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.text.buildAnnotatedString
import com.krzysobo.soboapptpl.viewmodel.SoboViewModel
import com.krzysobo.soboapptpl.widgets.strNotEmpty
import com.krzysobo.soboapptpl.widgets.validateWithLambda
import com.krzysobo.sobocryptolib.crypto.service.CryptoService

class EncryptPageVM : SoboViewModel() {
    var plaintext: MutableState<String> = mutableStateOf("")
    var ciphertextHex: MutableState<String> = mutableStateOf("")

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


    var isErrorPlaintext: MutableState<Boolean> = mutableStateOf(false)
    var isErrorSecretKeyHex: MutableState<Boolean> = mutableStateOf(false)
    var isErrorSecretKeyHexSrcPass: MutableState<Boolean> = mutableStateOf(false)

    fun doClearAll() {
        plaintext.value = ""
        ciphertextHex.value = ""
        secretKeyHex.value = ""
        secretKeyBytes.value = byteArrayOf()
        isSecretKeyHexVisible.value = false
        secretKeyHexSrcPass.value = ""
        isSecretKeyHexSrcPassVisible.value = false
        isSecretKeyHexSrcPassWidgetOpen.value = false

        clearErrors()
    }

    fun doGenerateRandomKey() {
        val key = CryptoService().makeRandomAesKey()
        secretKeyBytes.value = key
        secretKeyHex.value = CryptoService().bytesToHex(key)
        validateSecretKeyHex()
    }

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

    @OptIn(ExperimentalStdlibApi::class)
    fun doPastePlaintextFromClipboard(clipboardManager: ClipboardManager) {
        clipboardManager.getText()?.text?.let {
            if (it != "") {
                plaintext.value = it
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
    fun doCopyCiphertextToClipboard(clipboardManager: ClipboardManager) {
        println("COPYING CIPHERTEXT HEX TO CLIPBOARD -- 00 -- ${ciphertextHex.value}")
        if (validateSecretKeyHex()) {
            println("COPYING CIPHERTEXT HEX TO CLIPBOARD -- 01 - OK -- ${ciphertextHex.value}")
            clipboardManager.setText(buildAnnotatedString { append(ciphertextHex.value) })
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    suspend fun doEncrypt(): Boolean {
        println("\nEncrypting the text '${plaintext.value}'.... \n")
        try {
            val valRes = validateSecretKeyHex()
            if (!valRes) {
                println("\nSECRET KEY HEX ERROR!!!! \n")
                isFormSent.value = false
                isApiError.value = true
                apiErrorDetails.value = "Validation has failed. Check your data inputs."
                return false
            }

            println("---------> doEncrypt-11111")
            ciphertextHex.value = CryptoService().aesGcmEncryptToPortableHex(
                plaintext = plaintext.value.toByteArray(),
                aesKey = secretKeyBytes.value,
                nonce = CryptoService().makeNonce(),
                associatedData = CryptoService().makeAssociatedData()
            )
            println("---------> doEncrypt-222222")

            println("---------> doEncrypt-333333 ${ciphertextHex.value}")

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

    fun validate(): Boolean {
        clearErrors()
        val resPlaintext = validateWithLambda(isErrorPlaintext, { strNotEmpty(plaintext.value) })
        val resSecretKeyHex = validateSecretKeyHex()
        var res = resPlaintext && resSecretKeyHex

        if (isSecretKeyHexSrcPassWidgetOpen.value) {
            res = res && validateSecretKeyHexSrcPass()
        }

        return res
    }

    fun clearPlaintextError() {
        clearErrorFlag(isErrorPlaintext)
    }

    fun clearSecretKeyHexError() {
        clearErrorFlag(isErrorSecretKeyHex)
    }

    fun clearSecretKeyHexSrcPassError() {
        clearErrorFlag(isErrorSecretKeyHexSrcPass)
    }

    fun clearErrors() {
        clearPlaintextError()
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

