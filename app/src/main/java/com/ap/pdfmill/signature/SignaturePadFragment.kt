package com.ap.pdfmill.signature

import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.ap.pdfmill.MainActivity
import com.ap.pdfmill.MainViewModel
import com.ap.pdfmill.databinding.SignaturePadBinding
import com.github.gcacace.signaturepad.views.SignaturePad


class SignaturePadFragment : Fragment() {
    private var _binding: SignaturePadBinding? = null
    private val viewModel: MainViewModel by activityViewModels()

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
            Toast.makeText(
                activity,
                "Saved",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}