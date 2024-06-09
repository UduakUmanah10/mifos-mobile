package org.mifos.mobile.ui.beneficiary_application.presentation


import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import org.mifos.mobile.R
import org.mifos.mobile.core.ui.component.MifosOutlinedTextField
import org.mifos.mobile.core.ui.component.MifosOutlinedTextField2
import org.mifos.mobile.core.ui.component.MifosTopBar
import org.mifos.mobile.core.ui.theme.MifosMobileTheme


@Composable
fun BeneficiaryApplicationScreen(
    toolbartitle: String,
    viewState: BeneficiaryViewState,
    onBeneficiaryNameChanged: (String) -> Unit,
    onOfficeNameChanged: (String) -> Unit,
    onTransferLimitChanged: (Int) -> Unit,
    onGuarantorChanged: (String) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {

        MifosTopBar(navigateBack = { /*TODO*/ }) {

            Text(text = toolbartitle, modifier = Modifier, maxLines = 1)
        }

        MifosDropdonmenu()



        MifosOutlinedTextField2(
            value = viewState.credentials.beneficiaryNAme.beneficiaryName,
            onValueChange = { it -> onBeneficiaryNameChanged(it) },
            supportingText = "",
            label = R.string.beneficiary_name
        )

        MifosOutlinedTextField2(
            value = viewState.credentials.officeName.officeName,
            onValueChange = { it -> onOfficeNameChanged(it) },
            supportingText = "",
            label = R.string.office_name

        )
        MifosOutlinedTextField2(
            value = viewState.credentials.transferLimit.transferLimit.toString(),
            onValueChange = { onTransferLimitChanged(it.toInt()) },
            supportingText = "",
            label = R.string.enter_transfer_limit,
            keyboardType = KeyboardType.Number

        )
        MifosOutlinedTextField2(
            value = viewState.credentials.beneficiaryNAme.beneficiaryName,
            onValueChange = { it -> onGuarantorChanged(it) },
            supportingText = "",
            label = R.string.add_guarantor,

            )

        Button(
            onClick = {

            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 4.dp),
            contentPadding = PaddingValues(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isSystemInDarkTheme()) Color(
                    0xFF9bb1e3
                ) else Color(0xFF325ca8)
            )
        ) {

            Text(text = stringResource(id = R.string.submit_beneficiary))
        }
    }

}

@Composable
fun OutlinedTextFieldSample(
    value: String,
    onValueChange: (String) -> Unit,
    supportingText: String,
    label: String

) {

    OutlinedTextField(
        value = value,
        onValueChange = { it -> onValueChange(it) },
        label = { Text(label) })
}

@Preview(
    name = "Night Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Preview(
    name = "Day mode",
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
private fun IconsScreenPreview(
    @PreviewParameter(LoginViewStateProvider::class)
    logInViewState: BeneficiaryViewState
) {
    MifosMobileTheme {

        BeneficiaryApplicationScreen(
            "Beneficiary Update",
            viewState = logInViewState,
            {}, {}, {}, {}


        )

    }
}


class LoginViewStateProvider : PreviewParameterProvider<BeneficiaryViewState> {

    override val values: Sequence<BeneficiaryViewState>
        get() {
            val activeCredentials = BeneficiaryFormInput(
                selectAccountType = SelectAccountType(selectaccounttype = ""),
                accountNumber = AccountNumber(accountNumber = 0),
                officeName = OfficeName(officeName = ""),
                transferLimit = TransferLimit(transferLimit = 0),
                beneficiaryNAme = BeneficiaryName(beneficiaryName = "")
            )
            return sequenceOf(
                BeneficiaryViewState.InitialFormState,
                BeneficiaryViewState.ActiveFormState(activeCredentials),
                BeneficiaryViewState.CreatedSuccessfully,

                BeneficiaryViewState.ActiveFormState(
                    credentials = activeCredentials,
                    accountNumberErrorMessage = null,
                    officeNameInputErrorMessage = null,
                    transferLimitErrorMessage = null,
                    beneficiaryNameInputErrorMessage = null

                ),

                )
        }
}