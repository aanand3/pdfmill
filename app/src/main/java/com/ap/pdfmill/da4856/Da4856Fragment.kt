package com.ap.pdfmill.da4856

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ap.pdfmill.MainViewModel
import com.ap.pdfmill.R
import com.ap.pdfmill.R.id.nav_signature_pad
import com.ap.pdfmill.databinding.Da4856Binding
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


class Da4856Fragment : Fragment() {
  private var _binding: Da4856Binding? = null
  private val viewModel: MainViewModel by activityViewModels()

  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = Da4856Binding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding.generateButton.setOnClickListener {
      viewModel.setDa4856(parseFormFields())
      findNavController().navigate(nav_signature_pad)
    }
  }

  override fun onDestroyView() {
    _binding = null
    super.onDestroyView()
  }

  private fun parseFormFields() =
    Da4856(
      name = binding.nameET.text.toString(),
      date = binding.dateET.text.toString(),
      dodId = binding.dodIdET.text.toString(),
      rank = binding.rankET.text.toString(),
      organization = binding.organizationET.text.toString(),
      counselorTitle = binding.counselorTitleET.text.toString(),
      purpose = binding.purposeET.text.toString(),
      planOfAction = binding.planOfActionET.text.toString(),
    )

}