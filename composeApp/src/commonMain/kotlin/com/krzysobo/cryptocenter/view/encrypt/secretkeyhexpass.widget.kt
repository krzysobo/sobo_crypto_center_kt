package com.krzysobo.cryptocenter.view.encrypt

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import sobocryptocenter.composeapp.generated.resources.Res
import sobocryptocenter.composeapp.generated.resources.cancel
import sobocryptocenter.composeapp.generated.resources.from_password
import com.krzysobo.soboapptpl.pubres.PubRes as AppTplPubRes
import com.krzysobo.soboapptpl.service.AnyRes
import com.krzysobo.soboapptpl.service.anyResText
import com.krzysobo.soboapptpl.widgets.PasswordWidget
import com.krzysobo.cryptocenter.viewmodel.EncryptPageVM
import com.krzysobo.cryptocenter.viewmodel.getEncryptPageVM

@Composable
fun SecretKeyHexSrcPassWidget() {
    val vm: EncryptPageVM = getEncryptPageVM()

    // ===== "from password" password widget =====
    if (vm.isSecretKeyHexSrcPassWidgetOpen.value) {
        Column(
            modifier = Modifier
                .border(BorderStroke(width = 1.dp, color = Color.Blue))
                .padding(all = 10.dp)
        ) {

            PasswordWidget(
                value = vm.secretKeyHexSrcPass.value,
                onValueChanges = { data: String ->
                    vm.secretKeyHexSrcPass.value = data
                    vm.clearSecretKeyHexSrcPassError()
                    vm.validateSecretKeyHexSrcPass()
                },
                isError = vm.isErrorSecretKeyHexSrcPass.value,
//            focusManager = focusManager,
                labelText = anyResText(AnyRes(AppTplPubRes.string.password)),
                placeHolderText = anyResText(AnyRes(AppTplPubRes.string.password)),
                errorText = anyResText(AnyRes(AppTplPubRes.string.password_required)),
                trailingIconPassOnClick = { vm.toggleSecretKeyHexSrcPassVisible() },
                isPassVisible = vm.isSecretKeyHexSrcPassVisible.value,
                isReadOnly = false
            )

            Row {
                Button(
                    modifier = Modifier
                        .weight(0.25f)
                        .padding(end = 15.dp),
                    onClick = {
//                        println("Generate AES KEY FROM PASSWORD!!!")
//                    vm.openSecretKeyHexSrcPassWidget()
                    }) {
                    Text(anyResText(AnyRes(Res.string.from_password)))
                }

                OutlinedButton(
                    modifier = Modifier
                        .weight(0.25f)
                        .padding(end = 15.dp),
                    colors = ButtonDefaults.buttonColors(
//                        backgroundColor = MaterialTheme.colors.secondary,
                        backgroundColor = Color.White,
                        contentColor = MaterialTheme.colors.onSecondary
                    ),
                    onClick = {
//                        println("CANCEL GENERATION FROM PASSWORD")
                        vm.closeSecretKeyHexSrcPassWidget()
                    }) {
                    Text(anyResText(AnyRes(Res.string.cancel)))
                }
            }
        }
    }
}
