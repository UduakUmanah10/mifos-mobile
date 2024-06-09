package org.mifos.mobile.ui.beneficiary_application.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.github.therajanmaurya.sweeterror.SweetUIErrorHandler
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.mifos.mobile.R
import org.mifos.mobile.core.ui.theme.MifosMobileTheme
import org.mifos.mobile.databinding.FragmentBeneficiaryApplicationBinding
import org.mifos.mobile.models.beneficiary.Beneficiary
import org.mifos.mobile.models.beneficiary.BeneficiaryPayload
import org.mifos.mobile.models.beneficiary.BeneficiaryUpdatePayload
import org.mifos.mobile.models.templates.beneficiary.BeneficiaryTemplate
import org.mifos.mobile.ui.beneficiary.presentation.BeneficiaryScreen
import org.mifos.mobile.ui.enums.BeneficiaryState
import org.mifos.mobile.ui.fragments.base.BaseFragment
import org.mifos.mobile.utils.BeneficiaryUiState
import org.mifos.mobile.utils.Constants
import org.mifos.mobile.utils.Network
import org.mifos.mobile.utils.ParcelableAndSerializableUtils.getCheckedParcelable
import org.mifos.mobile.utils.ParcelableAndSerializableUtils.getCheckedSerializable
import org.mifos.mobile.utils.Toaster

/**
 * Created by dilpreet on 16/6/17.
 */


@AndroidEntryPoint
class BeneficiaryApplicationFragment : BaseFragment() {

   // private var _binding: FragmentBeneficiaryApplicationBinding? = null
    private val viewModel2: NewBeneficiaryViewModel by viewModels()

   //private val binding get() = _binding!!

    private val viewModel: BeneficiaryApplicationViewModel by viewModels()
    private lateinit var currentToolbarTitle:String

    private val listAccountType: MutableList<String?> = ArrayList()
    private var beneficiaryState: BeneficiaryState? = null
    private var beneficiary: Beneficiary? = null
    private var beneficiaryTemplate: BeneficiaryTemplate? = null
    private var accountTypeId: Int? = -1
    private var sweetUIErrorHandler: SweetUIErrorHandler? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.hide()
       // setToolbarTitle(getString(R.string.add_beneficiary))
        arguments?.let {
            beneficiaryState = it.getCheckedSerializable(
                BeneficiaryState::class.java,
                Constants.BENEFICIARY_STATE
            ) as BeneficiaryState

            beneficiary = it.getCheckedParcelable(
                Beneficiary::class.java,
                Constants.BENEFICIARY
            )

            val toolbarTitle = when (beneficiaryState) {
                BeneficiaryState.UPDATE -> getString(R.string.update_beneficiary)
                BeneficiaryState.CREATE_QR -> getString(R.string.add_beneficiary)
                else -> getString(R.string.add_beneficiary)
            }

           // setToolbarTitle(toolbarTitle)
            currentToolbarTitle =  toolbarTitle
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {


                MifosMobileTheme {
                    val vs = viewModel2.viewState.collectAsState()
                    BeneficiaryApplicationScreen(
                        toolbartitle = currentToolbarTitle,
                        viewState = vs.value,
                        onBeneficiaryNameChanged = viewModel2::changeBeneficiaryName,
                        onGuarantorChanged = {},
                        onOfficeNameChanged = viewModel2::changeOfficeName,
                        onTransferLimitChanged = viewModel2::changeTransferLimit
                    )

                }

            }

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        viewLifecycleOwner.lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.beneficiaryUiState.collect {
//                    when (it) {
//                        is BeneficiaryUiState.Loading -> showProgress()
//                        is BeneficiaryUiState.ShowErro.r -> {
//                            hideProgress()
//                            showError(getString(it.message))
//                        }
//
//                        is BeneficiaryUiState.SetVisibility -> {
//                            hideProgress()
//                            setVisibility(it.visibility)
//                        }
//
//                        is BeneficiaryUiState.ShowBeneficiaryTemplate -> {
//                            hideProgress()
//                            showBeneficiaryTemplate(it.beneficiaryTemplate)
//                        }
//
//                        is BeneficiaryUiState.CreatedSuccessfully -> {
//                            hideProgress()
//                            showBeneficiaryCreatedSuccessfully()
//                        }
//
//                        is BeneficiaryUiState.UpdatedSuccessfully -> {
//                            hideProgress()
//                            showBeneficiaryUpdatedSuccessfully()
//                        }
//
//                        is BeneficiaryUiState.Initial -> {}
//
//                        else -> throw IllegalStateException("Undesired $it")
//                    }
//                }
//            }
//        }

//        with(binding) {
//            btnBeneficiarySubmit.setOnClickListener {
//                submitBeneficiary()
//            }
//            layoutError.btnTryAgain.setOnClickListener {
//                onRetry()
//            }
//        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(Constants.TEMPLATE, beneficiaryTemplate)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState != null) {
            showBeneficiaryTemplate(
                savedInstanceState.getCheckedParcelable(
                    BeneficiaryTemplate::class.java,
                    Constants.TEMPLATE
                )
            )
        }
    }

    /**
     * Setting up `accountTypeAdapter` and `` spAccountType
     */
    fun showUserInterface() {
//        with(binding) {
//            accountTypeField.setOnItemClickListener { _, _, position, _ ->
//                accountTypeId = beneficiaryTemplate?.accountTypeOptions?.get(position)?.id
//            }
//            accountTypeFieldParent.isEnabled = Network.isConnected(context)
//            accountTypeField.setSimpleItems(listAccountType.toTypedArray())
//        }
    }

    /**
     * Fetches [BeneficiaryTemplate] from server and further updates the UI according to
     * [BeneficiaryState] which is initialized in `newInstance()` as
     * `beneficiaryState`
     *
     * @param beneficiaryTemplate [BeneficiaryTemplate] fetched from server
     */
    private fun showBeneficiaryTemplate(beneficiaryTemplate: BeneficiaryTemplate?) {
//        this.beneficiaryTemplate = beneficiaryTemplate
//        for ((_, _, value) in beneficiaryTemplate?.accountTypeOptions!!) {
//            listAccountType.add(value)
//        }
//        binding.accountTypeField.setSimpleItems(listAccountType.toTypedArray())
//        if (beneficiaryState == BeneficiaryState.UPDATE) {
//            with(binding) {
//                accountTypeField.setText(beneficiary?.accountType?.value!!, false)
//                accountTypeFieldParent.isEnabled = false
//                tilAccountNumber.editText?.setText(beneficiary?.accountNumber)
//                tilAccountNumber.isEnabled = false
//                tilOfficeName.editText?.setText(beneficiary?.officeName)
//                tilOfficeName.isEnabled = false
//                tilBeneficiaryName.editText?.setText(beneficiary?.name)
//                tilTransferLimit.editText?.setText(beneficiary?.transferLimit.toString())
//            }
//        } else if (beneficiaryState == BeneficiaryState.CREATE_QR) {
//            with(binding) {
//                accountTypeField.setText(beneficiary?.accountType?.value!!, false)
//                tilAccountNumber.editText?.setText(beneficiary?.accountNumber)
//                tilOfficeName.editText?.setText(beneficiary?.officeName)
//            }
//        }
    }

    /**
     * Used for submitting a new or updating beneficiary application
     */
    private fun submitBeneficiary() {
//        with(binding) {
//            tilAccountNumber.isErrorEnabled = false
//            tilOfficeName.isErrorEnabled = false
//            tilTransferLimit.isErrorEnabled = false
//            tilBeneficiaryName.isErrorEnabled = false
//        }
//        with(binding) {
//            when {
//                accountTypeId == -1 -> {
//                    Toaster.show(root, getString(R.string.choose_account_type))
//                    return
//                }
//
//                tilAccountNumber.editText?.text.isNullOrBlank() -> {
//                    tilAccountNumber.error = getString(R.string.enter_account_number)
//                    return
//                }
//
//                tilOfficeName.editText?.text.isNullOrBlank() -> {
//                    tilOfficeName.error = getString(R.string.enter_office_name)
//                    return
//                }
//
//                tilTransferLimit.editText?.text.toString().isEmpty() -> {
//                    tilTransferLimit.error = getString(R.string.enter_transfer_limit)
//                    return
//                }
//
//                tilTransferLimit.editText?.text.toString() == "." -> {
//                    tilTransferLimit.error = getString(R.string.invalid_amount)
//                    return
//                }
//
//                tilTransferLimit.editText?.text.toString().matches("^0*".toRegex()) -> {
//                    tilTransferLimit.error = getString(R.string.amount_greater_than_zero)
//                    return
//                }
//
//                tilBeneficiaryName.editText?.text.isNullOrBlank() -> {
//                    tilBeneficiaryName.error = getString(R.string.enter_beneficiary_name)
//                    return
//                }
//
////                else -> {}
////            }
//        }
//
//        if (beneficiaryState == BeneficiaryState.CREATE_MANUAL ||
//            beneficiaryState == BeneficiaryState.CREATE_QR
//        ) {
//            submitNewBeneficiaryApplication()
//        } else if (beneficiaryState == BeneficiaryState.UPDATE) {
//            submitUpdateBeneficiaryApplication()
//        }
    }

    private fun onRetry() {
     //   if (Network.isConnected(context)) {
         //   viewModel.loadBeneficiaryTemplate()
       //     sweetUIErrorHandler?.hideSweetErrorLayoutUI(
       //         binding.viewFlipper,
      //          binding.layoutError.root,
      //      )
    //    } else {
    //        Toaster.show(binding.root, getString(R.string.internet_not_connected))
     //   }
    }

    /**
     * Submit a new Beneficiary application
     */
    private fun submitNewBeneficiaryApplication() {
       // val beneficiaryPayload = BeneficiaryPayload()
      //  with(binding) {
       //     beneficiaryPayload.accountNumber = tilAccountNumber.editText?.text.toString()
     //       beneficiaryPayload.officeName = tilOfficeName.editText?.text.toString()
       //     beneficiaryPayload.name = tilBeneficiaryName.editText?.text.toString()
       //     beneficiaryPayload.transferLimit =
      //          tilTransferLimit.editText?.text.toString().toInt().toFloat()
       // }

     //   beneficiaryPayload.accountType = accountTypeId
     //   viewModel.createBeneficiary(beneficiaryPayload)
    }

    /**
     * Updates an existing beneficiary application
     */
    private fun submitUpdateBeneficiaryApplication() {
    //    val payload = BeneficiaryUpdatePayload()
      //  payload.name = binding.tilBeneficiaryName.editText?.text.toString()
     //   payload.transferLimit = binding.tilTransferLimit.editText?.text.toString().toFloat()
      //  viewModel.updateBeneficiary(beneficiary?.id?.toLong(), payload)
    }

    /**
     * Displays a {@link Snackbar} on successfully creation of
     * Beneficiary and pops fragments in order to go back to [BeneficiaryListFragment]
     */
    private fun showBeneficiaryCreatedSuccessfully() {
    //    Toaster.show(binding.tilTransferLimit, getString(R.string.beneficiary_created_successfully))
      //  activity?.finish()
    }

    /**
     * Displays a {@link Snackbar} on successfully updation of
     * Beneficiary and pops fragments in order to go back to [BeneficiaryListFragment]
     */
    private fun showBeneficiaryUpdatedSuccessfully() {
    //    Toaster.show(binding.root, getString(R.string.beneficiary_updated_successfully))
      //  activity?.supportFragmentManager?.popBackStack()
      //  activity?.supportFragmentManager?.popBackStack()
    }

    /**
     * It is called whenever any error occurs while executing a request
     *
     * @param msg Error message that tells the user about the problem.
     */
    fun showError(msg: String?) {
    //    if (!Network.isConnected(context)) {
      //      sweetUIErrorHandler?.showSweetNoInternetUI(
           //     binding.viewFlipper,
           //     binding.layoutError.root,
      //      )
      //  } else {
       ///     sweetUIErrorHandler?.showSweetErrorUI(
       //         msg,
       //         binding.viewFlipper,
      //          binding.layoutError.root,
      //      )
    //       Toaster.show(binding.root, msg)
       // }
    }

    private fun setVisibility(state: Int) {
     //   binding.llApplicationBeneficiary.visibility = state
    }

    fun showProgress() {
        showProgressBar()
    }

    fun hideProgress() {
     //   hideProgressBar()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as? AppCompatActivity)?.supportActionBar?.show()
        hideProgress()
    //    _binding = null
    }

    companion object {
        fun newInstance(
            beneficiaryState: BeneficiaryState?,
            beneficiary: Beneficiary?,
        ): BeneficiaryApplicationFragment {
            val fragment = BeneficiaryApplicationFragment()
            val args = Bundle()
            args.putSerializable(Constants.BENEFICIARY_STATE, beneficiaryState)
            if (beneficiary != null) {
                args.putParcelable(Constants.BENEFICIARY, beneficiary)
            }
            fragment.arguments = args
            return fragment
        }
    }
}