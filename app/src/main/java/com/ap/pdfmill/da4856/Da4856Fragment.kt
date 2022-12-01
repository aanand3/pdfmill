package com.ap.pdfmill.da4856

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ap.pdfmill.R
import com.ap.pdfmill.databinding.Da4856Binding


class Da4856Fragment : Fragment() {
  companion object {
    fun newInstance(): Da4856Fragment {
      return Da4856Fragment()
    }
  }

  private var _binding: Da4856Binding? = null

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
      activity?.resources?.openRawResource(R.raw.da4856).use { inputStream ->
        val da31 = parseFormFields()
        val byteArrayOutputStream = exportPdf(inputStream, da31)

        val filename = "myfile"
        context?.openFileOutput(filename, MODE_PRIVATE).use {
          it?.write(byteArrayOutputStream.toByteArray())
        }
      }
      findNavController().popBackStack()
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