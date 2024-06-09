package org.mifos.mobile.ui.beneficiary_application.presentation

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import org.mifos.mobile.core.ui.theme.MifosMobileTheme

@Composable
fun BeneficiaryPage(
    toolbartitle: String,
    viewState: BeneficiaryViewState,
    onBeneficiaryNameChanged: (String) -> Unit,
    onOfficeNameChanged: (String) -> Unit,
    onTransferLimitChanged: (Int) -> Unit,
    onGuarantorChanged: (String) -> Unit

) {

    Box(modifier = Modifier.fillMaxHeight().fillMaxWidth()) {
        BeneficiaryApplicationScreen(
            toolbartitle = toolbartitle,
            viewState = viewState,
            onBeneficiaryNameChanged = onBeneficiaryNameChanged,
            onOfficeNameChanged = onOfficeNameChanged,
            onTransferLimitChanged = onTransferLimitChanged,
            onGuarantorChanged = onGuarantorChanged
        )
        CircularProgressIndicator(
            modifier = Modifier.align(alignment = Alignment.Center).padding(bottom = 20.dp)
        )
    }

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
private fun BeneficiaryPagePreview(
    @PreviewParameter(LoginViewStateProvider1::class)
    logInViewState: BeneficiaryViewState
) {
    MifosMobileTheme {
        BeneficiaryPage(
           toolbartitle = "Add ",
           viewState = logInViewState,
           onBeneficiaryNameChanged ={} ,
           onOfficeNameChanged = {},
           onTransferLimitChanged = {},
            onGuarantorChanged = {}
       )

    }
}

class LoginViewStateProvider1 : PreviewParameterProvider<BeneficiaryViewState> {

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