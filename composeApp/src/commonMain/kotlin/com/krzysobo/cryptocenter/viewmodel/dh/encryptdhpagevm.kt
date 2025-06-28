package com.krzysobo.cryptocenter.viewmodel.dh

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.text.buildAnnotatedString
import com.krzysobo.soboapptpl.viewmodel.SoboViewModel
import com.krzysobo.soboapptpl.widgets.strNotEmpty
import com.krzysobo.soboapptpl.widgets.validateWithLambda
import com.krzysobo.sobocryptolib.crypto.service.CryptoService
import com.krzysobo.sobocryptolib.crypto.service.X25519KeyPairBytes

class EncryptDhPageVM : SoboViewModel() {
    var plaintext: MutableState<String> = mutableStateOf("")
    var ciphertextHex: MutableState<String> = mutableStateOf("")

    // ---- secret key hex -----
    var secretKeyHex: MutableState<String> = mutableStateOf("")
    var secretKeyBytes: MutableState<ByteArray> = mutableStateOf(byteArrayOf())
    var isSecretKeyHexVisible: MutableState<Boolean> = mutableStateOf(false)
    // ---- /secret key hex -----

    // ---- OUR public key hex -----
    var ourPublicKeyHex: MutableState<String> = mutableStateOf("")
    var ourPublicKeyBytes: MutableState<ByteArray> = mutableStateOf(byteArrayOf())
    var isOurPublicKeyHexVisible: MutableState<Boolean> = mutableStateOf(false)
    // ---- /OUR public key hex -----

    // ---- THEIR public key hex -----
    var theirPublicKeyHex: MutableState<String> = mutableStateOf("")
    var theirPublicKeyBytes: MutableState<ByteArray> = mutableStateOf(byteArrayOf())
    var isTheirPublicKeyHexVisible: MutableState<Boolean> = mutableStateOf(false)
    // ---- /OUR public key hex -----

    var isErrorPlaintext: MutableState<Boolean> = mutableStateOf(false)
    var isErrorCiphertext: MutableState<Boolean> = mutableStateOf(false)
    var isErrorSecretKeyHex: MutableState<Boolean> = mutableStateOf(false)
    var isErrorOurPublicKeyHex: MutableState<Boolean> = mutableStateOf(false)
    var isErrorTheirPublicKeyHex: MutableState<Boolean> = mutableStateOf(false)



    fun doClearAll() {
        plaintext.value = ""
        ciphertextHex.value = ""

        secretKeyHex.value = ""
        secretKeyBytes.value = byteArrayOf()
        isSecretKeyHexVisible.value = false

        ourPublicKeyHex.value = ""
        ourPublicKeyBytes.value = byteArrayOf()
        isOurPublicKeyHexVisible.value = false

        theirPublicKeyHex.value = ""
        theirPublicKeyBytes.value = byteArrayOf()
        isTheirPublicKeyHexVisible.value = false

        clearErrors()
    }

    fun doGenerateDhKeyPair() {
        // TODO TODO
        val pair: X25519KeyPairBytes = CryptoService().makeDhKeyPairAsBytes()

        secretKeyBytes.value = pair.private
        secretKeyHex.value = CryptoService().bytesToHex(pair.private)

        ourPublicKeyBytes.value = pair.public
        ourPublicKeyHex.value = CryptoService().bytesToHex(pair.public)

        validateSecretKeyHex(secretKeyHex.value)
        validateOurPublicKeyHex(ourPublicKeyHex.value)
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun doPasteSecretKeyHexFromClipboard(clipboardManager: ClipboardManager) {
        clipboardManager.getText()?.text?.let {
            if (validateSecretKeyHex(it)) {
                secretKeyHex.value = it
                secretKeyBytes.value = CryptoService().hexToBytes(it)
            }
        }
    }

    fun doPasteOurPublicKeyHexFromClipboard(clipboardManager: ClipboardManager) {
        clipboardManager.getText()?.text?.let {
            if (validateOurPublicKeyHex(it)) {
                ourPublicKeyHex.value = it
                ourPublicKeyBytes.value = CryptoService().hexToBytes(it)
            }
        }
    }

    fun doPasteTheirPublicKeyHexFromClipboard(clipboardManager: ClipboardManager) {
        clipboardManager.getText()?.text?.let {
            if (validateTheirPublicKeyHex(it)) {
                theirPublicKeyHex.value = it
                theirPublicKeyBytes.value = CryptoService().hexToBytes(it)
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
    fun doCopySecretKeyHexToClipboard(clipboardManager: ClipboardManager) {
        val key = secretKeyHex.value
        println("COPYING SECRET KEY HEX TO CLIPBOARD -- 00 -- $key")
        if (validateSecretKeyHex(key)) {
            println("COPYING HEX KEY TO CLIPBOARD -- 01 - OK -- $key")
            clipboardManager.setText(buildAnnotatedString { append(key) })
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun doCopyOurPublicKeyHexToClipboard(clipboardManager: ClipboardManager) {
        val key = ourPublicKeyHex.value
        println("COPYING OUR PUBLIC KEY HEX TO CLIPBOARD -- 00 -- $key")
        if (validateOurPublicKeyHex(key)) {
            println("COPYING OUR PUBLIC KEY HEX TO CLIPBOARD -- 01 - OK -- $key")
            clipboardManager.setText(buildAnnotatedString { append(key) })
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun doCopyTheirPublicKeyHexToClipboard(clipboardManager: ClipboardManager) {
        val key = theirPublicKeyHex.value
        println("COPYING THEIR PUBLIC KEY HEX TO CLIPBOARD -- 00 -- $key")
        if (validateTheirPublicKeyHex(key)) {
            println("COPYING THEIR PUBLIC KEY HEX TO CLIPBOARD -- 01 - OK -- $key")
            clipboardManager.setText(buildAnnotatedString { append(key) })
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun doCopyCiphertextToClipboard(clipboardManager: ClipboardManager) {
        println("COPYING CIPHERTEXT HEX TO CLIPBOARD -- 00 -- ${ciphertextHex.value}")
        if (validateCiphertextHex(ciphertextHex.value)) {
            println("COPYING CIPHERTEXT HEX TO CLIPBOARD -- 01 - OK -- ${ciphertextHex.value}")
            clipboardManager.setText(buildAnnotatedString { append(ciphertextHex.value) })
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    suspend fun doEncrypt(): Boolean {
        println("\nEncrypting the text '${plaintext.value}'.... \n")
        try {
            val valRes = validate()
            if (!valRes) {
                println("\nVALIDATION ERROR!!!! \n")
                isFormSent.value = false
                isApiError.value = true
                apiErrorDetails.value = "Validation has failed. Check your data inputs."
                return false
            }

            val sharedSecret = CryptoService().makeDhSharedSecretFromHex(
                ourPrivateKeyHex = secretKeyHex.value,
                theirPublicKeyHex = theirPublicKeyHex.value)

            val aesKey = CryptoService().deriveAesKeyFromSharedSecretSimple(sharedSecret)

            ciphertextHex.value = CryptoService().aesGcmEncryptToPortableHexForDh(
                plaintext = plaintext.value.toByteArray(),
                aesKey = aesKey,
                nonce = CryptoService().makeNonce(),
                ourPublicKey = ourPublicKeyBytes.value,
                associatedData = CryptoService().makeAssociatedData(),
                salt = null
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

    fun validateSecretKeyHex(key: String): Boolean {
        val res = CryptoService().isDhSecretKeyHexValid(key)
        isErrorSecretKeyHex.value = !res
        return res
    }

    fun validateOurPublicKeyHex(key: String): Boolean {
        val res = CryptoService().isDhPublicKeyHexValid(key)
        isErrorOurPublicKeyHex.value = !res
        return res
    }

    fun validateTheirPublicKeyHex(key: String): Boolean {
        val res = CryptoService().isDhPublicKeyHexValid(key)
        isErrorTheirPublicKeyHex.value = !res
        return res
    }

    // TODO - move to Decrypt DH!!!
    fun validateCiphertextHex(ciphertext: String): Boolean {
        val res = strNotEmpty(ciphertextHex.value)
        isErrorCiphertext.value = !res
        return res
    }

    fun validate(): Boolean {
        clearErrors()
        val resPlaintext = validateWithLambda(isErrorPlaintext, { strNotEmpty(plaintext.value) })
        val resSecretKeyHex = validateSecretKeyHex(secretKeyHex.value)
        val resOurPublicKeyHex = validateOurPublicKeyHex(ourPublicKeyHex.value)
        val resTheirPublicKeyHex = validateTheirPublicKeyHex(theirPublicKeyHex.value)
        var res = resPlaintext && resSecretKeyHex && resOurPublicKeyHex && resTheirPublicKeyHex

        return res
    }

    fun clearPlaintextError() {
        clearErrorFlag(isErrorPlaintext)
    }

    fun clearSecretKeyHexError() {
        clearErrorFlag(isErrorSecretKeyHex)
    }

    fun clearOurPublicKeyHexError() {
        clearErrorFlag(isErrorOurPublicKeyHex)
    }

    fun clearTheirPublicKeyHexError() {
        clearErrorFlag(isErrorTheirPublicKeyHex)
    }

    fun clearErrors() {
        clearPlaintextError()
        clearSecretKeyHexError()
        clearOurPublicKeyHexError()
        clearTheirPublicKeyHexError()

        clearApiError()
        clearFormSent()
    }

    fun toggleSecretKeyHexVisible() {
        isSecretKeyHexVisible.value = !isSecretKeyHexVisible.value
    }

    fun toggleOurPublicKeyHexVisible() {
        isOurPublicKeyHexVisible.value = !isOurPublicKeyHexVisible.value
    }

    fun toggleTheirPublicKeyHexVisible() {
        isTheirPublicKeyHexVisible.value = !isTheirPublicKeyHexVisible.value
    }



}

