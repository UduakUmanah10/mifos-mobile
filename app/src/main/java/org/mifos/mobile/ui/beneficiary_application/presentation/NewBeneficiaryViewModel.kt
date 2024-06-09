package org.mifos.mobile.ui.beneficiary_application.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NewBeneficiaryViewModel(

) : ViewModel() {


    private val _viewState: MutableStateFlow<BeneficiaryViewState> =
        MutableStateFlow(BeneficiaryViewState.InitialFormState)

    val viewState: StateFlow<BeneficiaryViewState> = _viewState

    val completedState = Channel<Unit>()

    fun changeAccountNumber(accountNumber: Int) {
        val currentCredentials = _viewState.value.credentials
        val currentofficeNameErrorMessage =
            (_viewState.value as? BeneficiaryViewState.ActiveFormState)?.officeNameInputErrorMessage

        val currentofTransferlimitErrorMessage =
            (_viewState.value as? BeneficiaryViewState.ActiveFormState)?.transferLimitErrorMessage

        val currentBeneficiaryNameErrorMessage =
            (_viewState.value as? BeneficiaryViewState.ActiveFormState)?.beneficiaryNameInputErrorMessage

        val currentAccountNumberErrorMessage =
            (_viewState.value as? BeneficiaryViewState.ActiveFormState)?.accountNumberErrorMessage

        _viewState.value = BeneficiaryViewState.ActiveFormState(
            credentials = currentCredentials.withUpdatedAccountNumber(accountNumber),
            accountNumberErrorMessage = null,
            officeNameInputErrorMessage = currentofficeNameErrorMessage,
            transferLimitErrorMessage = currentofTransferlimitErrorMessage,
            beneficiaryNameInputErrorMessage = currentBeneficiaryNameErrorMessage
        )
    }

    fun changeOfficeName(officeName: String) {
        val currentCredentials = _viewState.value.credentials

        val currentofficeNameErrorMessage =
            (_viewState.value as? BeneficiaryViewState.ActiveFormState)?.officeNameInputErrorMessage

        val currentofTransferlimitErrorMessage =
            (_viewState.value as? BeneficiaryViewState.ActiveFormState)?.transferLimitErrorMessage

        val currentBeneficiaryNameErrorMessage =
            (_viewState.value as? BeneficiaryViewState.ActiveFormState)?.beneficiaryNameInputErrorMessage

        val currentAccountNumberErrorMessage =
            (_viewState.value as? BeneficiaryViewState.ActiveFormState)?.accountNumberErrorMessage

        _viewState.value = BeneficiaryViewState.ActiveFormState(
            credentials = currentCredentials.withUpdatedOfficeName(officeName),
            accountNumberErrorMessage = currentAccountNumberErrorMessage,
            officeNameInputErrorMessage = null,
            transferLimitErrorMessage = currentofTransferlimitErrorMessage,
            beneficiaryNameInputErrorMessage = currentBeneficiaryNameErrorMessage
        )
    }

    fun changeTransferLimit(transferLimit: Int) {
        val currentCredentials = _viewState.value.credentials

        val currentofficeNameErrorMessage =
            (_viewState.value as? BeneficiaryViewState.ActiveFormState)?.officeNameInputErrorMessage

        val currentofTransferlimitErrorMessage = null

        val currentBeneficiaryNameErrorMessage =
            (_viewState.value as? BeneficiaryViewState.ActiveFormState)?.beneficiaryNameInputErrorMessage

        val currentAccountNumberErrorMessage =
            (_viewState.value as? BeneficiaryViewState.ActiveFormState)?.accountNumberErrorMessage

        _viewState.value = BeneficiaryViewState.ActiveFormState(
            credentials = currentCredentials.withUpdatedTranferLimit(transferLimit),
            accountNumberErrorMessage = currentAccountNumberErrorMessage,
            officeNameInputErrorMessage = null,
            transferLimitErrorMessage = currentofTransferlimitErrorMessage,
            beneficiaryNameInputErrorMessage = currentBeneficiaryNameErrorMessage
        )
    }


    fun changeBeneficiaryName(beneficiaryName: String) {
        val currentCredentials = _viewState.value.credentials
        val currentofficeNameErrorMessage =
            (_viewState.value as? BeneficiaryViewState.ActiveFormState)?.officeNameInputErrorMessage

        val currentofTransferlimitErrorMessage =
            (_viewState.value as? BeneficiaryViewState.ActiveFormState)?.transferLimitErrorMessage

        val currentBeneficiaryNameErrorMessage = null

        val currentAccountNumberErrorMessage =
            (_viewState.value as? BeneficiaryViewState.ActiveFormState)?.accountNumberErrorMessage

        _viewState.value = BeneficiaryViewState.ActiveFormState(
            credentials = currentCredentials.withUpdatedBeneficiaryName(beneficiaryName),
            accountNumberErrorMessage = currentAccountNumberErrorMessage,
            officeNameInputErrorMessage = null,
            transferLimitErrorMessage = currentofTransferlimitErrorMessage,
            beneficiaryNameInputErrorMessage = currentBeneficiaryNameErrorMessage
        )
    }


    private fun BeneficiaryFormInput.withUpdatedAccountType(accountType: String): BeneficiaryFormInput {
        return this.copy(
            selectAccountType = SelectAccountType(accountType),
        )
    }

    private fun BeneficiaryFormInput.withUpdatedAccountNumber(accountNumber: Int): BeneficiaryFormInput {
        return this.copy(
            accountNumber = AccountNumber(accountNumber),
        )
    }

    private fun BeneficiaryFormInput.withUpdatedOfficeName(officeName: String): BeneficiaryFormInput {
        return this.copy(
            officeName = OfficeName(officeName),
        )
    }

    private fun BeneficiaryFormInput.withUpdatedTranferLimit(transferLimit: Int): BeneficiaryFormInput {
        return this.copy(
            transferLimit = TransferLimit(transferLimit),
        )
    }

    private fun BeneficiaryFormInput.withUpdatedBeneficiaryName(beneficiaryName: String): BeneficiaryFormInput {
        return this.copy(
            beneficiaryNAme = BeneficiaryName(beneficiaryName),
        )
    }

}