package com.ap.pdfmill.signature

import android.R
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ap.pdfmill.MainActivity
import com.ap.pdfmill.MainViewModel
import com.ap.pdfmill.R.id.nav_host_fragment_content_main
import com.ap.pdfmill.da4856.Da4856
import com.ap.pdfmill.da4856.exportPdf
import com.ap.pdfmill.databinding.SignaturePadBinding
import com.github.gcacace.signaturepad.views.SignaturePad
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME


class SignaturePadFragment : Fragment() {
    private var _binding: SignaturePadBinding? = null
    private val viewModel: MainViewModel by activityViewModels()
    lateinit var da4856: Da4856

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SignaturePadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.observeDa4856().observe(viewLifecycleOwner) {
            da4856 = it
        }

        binding.signaturePad.setOnSignedListener(object : SignaturePad.OnSignedListener {
            override fun onStartSigning() {
                //Event triggered when the pad is touched
            }

            override fun onSigned() {
                //Event triggered when the pad is signed
                binding.clearButton.isEnabled = true
                binding.saveButton.isEnabled = true
                Log.d("signature", "complete!")
            }

            override fun onClear() {
                binding.clearButton.isEnabled = false
                binding.saveButton.isEnabled = false
                //Event triggered when the pad is cleared
            }
        })

        binding.clearButton.setOnClickListener {
            binding.signaturePad.clear()
        }

        binding.saveButton.setOnClickListener {
            activity?.resources?.openRawResource(com.ap.pdfmill.R.raw.da4856).use { inputStream ->
                val byteArrayOutputStream = exportPdf(inputStream, da4856, binding.signaturePad.signatureBitmap)

                val filename = "DA4856 " + ZonedDateTime.now().format(ISO_LOCAL_DATE_TIME)
                Log.d("filename", filename)
                viewModel.setFileName(filename)
                context?.openFileOutput(filename, MODE_PRIVATE).use {
                    it?.write(byteArrayOutputStream.toByteArray())
                }
            }
            Toast.makeText(
                activity,
                "Saved!",
                LENGTH_LONG
            ).show()
            findNavController().navigate(nav_host_fragment_content_main)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}