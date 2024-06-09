package org.mifos.mobile.ui.beneficiary_application.presentation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.mifos.mobile.models.beneficiary.Beneficiary


@Parcelize
sealed class BeneficiaryViewState(
    open val credentials: BeneficiaryFormInput,
    open val buttonsEnabled: Boolean = true,
    open val beneficiaries: List<Beneficiary?> = emptyList()
):Parcelable {
    data object InitialFormState : BeneficiaryViewState(
        credentials = BeneficiaryFormInput(),

        )

    data class ActiveFormState(
        override val credentials: BeneficiaryFormInput,
        val accountNumberErrorMessage: String? = null,
        val officeNameInputErrorMessage: String? = null,
        val transferLimitErrorMessage: String? = null,
        val beneficiaryNameInputErrorMessage: String? = null,
    )
        : BeneficiaryViewState(
        credentials = credentials,
    )

    data class SubmittingFormState(
        override val credentials: BeneficiaryFormInput,
    ) : BeneficiaryViewState(
        credentials = credentials,
        buttonsEnabled = false,
    )

    object UpdatedSuccessfully :BeneficiaryViewState( credentials = BeneficiaryFormInput())

    data class SubmissionError(
        override val credentials: BeneficiaryFormInput,
        val errorMessage: String,
    ) : BeneficiaryViewState(
        credentials = credentials,
    )
    data object CreatedSuccessfully : BeneficiaryViewState(
        BeneficiaryFormInput(),
        buttonsEnabled = false,
    )
    data object DeletedSuccessfully:BeneficiaryViewState(
        credentials = BeneficiaryFormInput()
    )


    data class ShowBeneficiaryList( override val beneficiaries: List<Beneficiary?>) :
        BeneficiaryViewState(
            credentials = BeneficiaryFormInput(),
            beneficiaries =beneficiaries
        )
}