package org.mifos.mobile.ui.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.zxing.Result
import dagger.hilt.android.AndroidEntryPoint
import me.dm7.barcodescanner.zxing.ZXingScannerView
import me.dm7.barcodescanner.zxing.ZXingScannerView.ResultHandler
import org.mifos.mobile.R
import org.mifos.mobile.databinding.FragmentScanQrCodeBinding
import org.mifos.mobile.models.beneficiary.Beneficiary
import org.mifos.mobile.ui.activities.base.BaseActivity
import org.mifos.mobile.ui.beneficiary_application.BeneficiaryApplicationComposeFragment
import org.mifos.mobile.ui.enums.BeneficiaryState
import org.mifos.mobile.ui.fragments.base.BaseFragment

/**
 * Created by dilpreet on 6/7/17.
 */
@AndroidEntryPoint
class QrCodeReaderFragment : BaseFragment(), ResultHandler {

    private var _binding: FragmentScanQrCodeBinding? = null
    private val binding get() = _binding!!

    private var flashOn = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentScanQrCodeBinding.inflate(inflater, container, false)
        setToolbarTitle(getString(R.string.add_beneficiary))
        binding.viewScanner.setAutoFocus(true)
        binding.btnFlash.setOnClickListener {
            turnOnFlash()
        }
        return binding.root
    }

    /**
     * Sets the [me.dm7.barcodescanner.zxing.ZXingScannerView.ResultHandler] callback and
     * opens Camera
     */
    override fun onResume() {
        super.onResume()
        binding.viewScanner.setResultHandler(this)
        binding.viewScanner.startCamera()
    }

    @Suppress("DEPRECATION")
    fun turnOnFlash() {
        if (flashOn) {
            flashOn = false
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                binding.btnFlash.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.ic_flash_on,
                        null,
                    ),
                )
            } else {
                binding.btnFlash.setImageDrawable(resources.getDrawable(R.drawable.ic_flash_on))
            }
            binding.viewScanner.flash = false
        } else {
            flashOn = true
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                binding.btnFlash.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.ic_flash_off,
                        null,
                    ),
                )
            } else {
                binding.btnFlash.setImageDrawable(resources.getDrawable(R.drawable.ic_flash_off))
            }
            binding.viewScanner.flash = true
        }
    }

    /**
     * Closes the Camera
     */
    override fun onPause() {
        super.onPause()
        binding.viewScanner.stopCamera()
    }

    /**
     * Callback for [ZXingScannerView] which retrieves data from QRCode
     *
     * @param result Contains data scanned from QRCode
     */
    override fun handleResult(result: Result) {
        val gson = Gson()
        try {
            val beneficiary = gson.fromJson(result.text, Beneficiary::class.java)
            activity?.supportFragmentManager?.popBackStack()
            (activity as BaseActivity?)?.replaceFragment(
                BeneficiaryApplicationComposeFragment.newInstance(
                    BeneficiaryState.CREATE_QR,
                    beneficiary,
                ),
                true,
                R.id.container,
            )
        } catch (e: JsonSyntaxException) {
            Toast.makeText(
                activity,
                getString(R.string.invalid_qr),
                Toast.LENGTH_SHORT,
            ).show()
            binding.viewScanner.resumeCameraPreview(this)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(): QrCodeReaderFragment {
            return QrCodeReaderFragment()
        }
    }
}
