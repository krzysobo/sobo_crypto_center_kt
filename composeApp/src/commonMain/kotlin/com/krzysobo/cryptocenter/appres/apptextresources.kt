package com.krzysobo.cryptocenter.appres

import com.krzysobo.cryptocenter.generated.SoboRes


object AppRes {
    object string {
        val dh_ops = SoboRes.string.dh_ops
        val about_s = SoboRes.string.about_s
        val app_name_sobo_crypto_center_desktop =
            SoboRes.string.app_name_sobo_crypto_center_desktop
        val cancel = SoboRes.string.cancel
        val cipher_text_hex = SoboRes.string.cipher_text_hex
        val clear_all = SoboRes.string.clear_all
        val copy = SoboRes.string.copy
        val copy_hex_key = SoboRes.string.copy_hex_key
        val copy_result_ciphertext = SoboRes.string.copy_result_ciphertext
        val copy_result_plaintext = SoboRes.string.copy_result_plaintext
        val cryptocenter_decryption = SoboRes.string.cryptocenter_decryption
        val cryptocenter_decryption_dh = SoboRes.string.cryptocenter_decryption_dh
        val cryptocenter_encryption = SoboRes.string.cryptocenter_encryption
        val cryptocenter_encryption_dh = SoboRes.string.cryptocenter_encryption_dh
        val decrypt = SoboRes.string.decrypt
        val decrypt_dh = SoboRes.string.decrypt_dh
        val decryption_error_header = SoboRes.string.decryption_error_header
        val encrypt = SoboRes.string.encrypt
        val encrypt_dh = SoboRes.string.encrypt_dh
        val encryption_error_header = SoboRes.string.encryption_error_header
        val error_unknown_error = SoboRes.string.error_unknown_error
        val from_password = SoboRes.string.from_password
        val generate_key_pair = SoboRes.string.generate_key_pair
        val generate_random = SoboRes.string.generate_random
        val get_secret_key = SoboRes.string.get_secret_key
        val help_s = SoboRes.string.help_s
        val more_at_url_s = SoboRes.string.more_at_url_s
        val our_public_key_hex = SoboRes.string.our_public_key_hex
        val our_public_key_hex_required = SoboRes.string.our_public_key_hex_required
        val our_secret_key_hex = SoboRes.string.our_secret_key_hex
        val our_secret_key_hex_required = SoboRes.string.our_secret_key_hex_required
        val paste = SoboRes.string.paste
        val paste_ciphertext = SoboRes.string.paste_ciphertext
        val paste_hex_key = SoboRes.string.paste_hex_key
        val paste_our_public_key = SoboRes.string.paste_our_public_key
        val paste_our_secret_key = SoboRes.string.paste_our_secret_key
        val paste_plaintext = SoboRes.string.paste_plaintext
        val plaintext = SoboRes.string.plaintext
        val plaintext_required = SoboRes.string.plaintext_required
        val s_settings = SoboRes.string.s_settings
        val secret_key_hex = SoboRes.string.secret_key_hex
        val secret_key_hex_required = SoboRes.string.secret_key_hex_required
        val settings_updated_ok = SoboRes.string.settings_updated_ok
        val settings_updated_ok_desc = SoboRes.string.settings_updated_ok_desc
        val settings_updating_error = SoboRes.string.settings_updating_error
        val their_public_key_hex = SoboRes.string.their_public_key_hex
        val their_public_key_hex_required = SoboRes.string.their_public_key_hex_required
        val update_settings = SoboRes.string.update_settings
        val welcome_s = SoboRes.string.welcome_s
        val welcome_story_sobo_crypto_center = SoboRes.string.welcome_story_sobo_crypto_center
    }
}


////@OptIn(InternalResourceApi::class)
////fun aboutX(): String {
////    val res = getResAboutDe()
////    return res.toString()
////}
////
//
////@OptIn(InternalResourceApi::class)
////fun ResourceEnvironment.publ(languageQualifier: LanguageQualifier) {
////    ResourceEnvironment.
////    return ResourceEnvironment(
////        LanguageQualifier("en"),
////        RegionQualifier("EU"),
////        ThemeQualifier.LIGHT,
////        DensityQualifier.HDPI
////    )
////
////}
////
////@OptIn(InternalResourceApi::class, ExperimentalResourceApi::class)
////fun getResAboutDe(): ResourceItem {
////    ResourceEnvironment(
////        LanguageQualifier("en"),
////        RegionQualifier("EU"),
////        ThemeQualifier.LIGHT,
////        DensityQualifier.HDPI
////    )
////
////
////    val x = stringResource()
////    return org.jetbrains.compose.resources.ResourceItem(setOf(org.jetbrains.compose.resources.LanguageQualifier("de"),
////    ),
////        "composeResources/sobocryptocenter.composeapp.generated.resources/values-de/strings.commonMain.cvr",
////        42, 21)
////
////}
//////
//////
//////
//////
//////    org.jetbrains.compose.resources.ResourceItem(setOf(org.jetbrains.compose.resources.LanguageQualifier("fr"),
//////    ),
//////        "composeResources/sobocryptocenter.composeapp.generated.resources/values-fr/strings.commonMain.cvr",
//////        104, 48)
//////}
//
//
//class SoboResourceNotFoundException(message: String) : Exception(message)
//
//class SoboStringResource(
//    var langVersion: HashMap<String, String> = hashMapOf()
//) {
//
//    // TODO - add plurals etc
//}
//
//typealias SoboStringResourceMap = HashMap<String, SoboStringResource>
//
//open class SoboResourceKeeper(
//    private var resMap: SoboStringResourceMap = hashMapOf(),
//    val useDefaultLang: Boolean = false
//) {
//
//    fun setResourcesFromXml(filePath: String, langForFile: String) {
//        // TODO
//    }
//
//    fun getByHandle(handle: String): SoboStringResource {
//        if (resMap.containsKey(handle)) {
//            return resMap[handle]!!
//        }
//
//        throw SoboResourceNotFoundException("string resource by handle $handle does not exist")
//    }
//}
