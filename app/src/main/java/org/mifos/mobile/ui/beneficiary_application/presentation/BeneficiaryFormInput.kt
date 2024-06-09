package org.mifos.mobile.ui.beneficiary_application.presentation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@JvmInline
@Parcelize
value class AccountNumber(val accountNumber: Int?):Parcelable

@JvmInline
@Parcelize
value class OfficeName(val officeName: String):Parcelable

@JvmInline
@Parcelize
value class TransferLimit(val transferLimit: Int):Parcelable

@JvmInline
@Parcelize
value class BeneficiaryName(val beneficiaryName: String):Parcelable

@JvmInline
@Parcelize
value class SelectAccountType(val selectaccounttype: String):Parcelable





@Parcelize
data class BeneficiaryFormInput(
    val selectAccountType:SelectAccountType= SelectAccountType(selectaccounttype = ""),
    val accountNumber: AccountNumber = AccountNumber(accountNumber = 0),
    val officeName: OfficeName = OfficeName(officeName = ""),
    val transferLimit:TransferLimit = TransferLimit(transferLimit = 0),
    val beneficiaryNAme:BeneficiaryName =BeneficiaryName(beneficiaryName = "")

):Parcelable