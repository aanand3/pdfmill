package com.ap.pdfmill.da31

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ap.pdfmill.R
import com.ap.pdfmill.databinding.Da31Binding
import java.io.File
import java.io.FileOutputStream

class Da31Fragment : Fragment() {
  private var _binding: Da31Binding? = null

  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = Da31Binding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
//    val mainActivity = requireActivity() as MainActivity

    binding.backButton.setOnClickListener {
      findNavController().popBackStack()
    }

    binding.generateButton.setOnClickListener {
      activity?.resources?.openRawResource(R.raw.da4856).use { inputStream ->
        val da31 = parseFormFields()
        val byteArrayOutputStream = exportPdf(inputStream, da31)

        val filename = "myfile"
        context?.openFileOutput(filename, MODE_PRIVATE).use {
          it?.write(byteArrayOutputStream.toByteArray())
        }
      }
    }
  }

  override fun onDestroyView() {
    _binding = null
    super.onDestroyView()
  }

  private fun parseFormFields() =
    Da31(
      name = binding.nameET.text.toString(),
      date = binding.dateET.text.toString(),
      dodId = binding.dodIdET.text.toString(),
      rank = binding.rankET.text.toString(),
      leaveAddress = binding.leaveAddressET.text.toString(),
      orgInfo = binding.orgInfoET.text.toString(),
      leaveBalance = binding.leaveBalanceET.text.toString(),
      startDate = binding.startDateET.text.toString(),
      daysRequested = binding.daysRequestedET.text.toString()
    )

}