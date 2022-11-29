package com.ap.pdfmill

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ap.pdfmill.databinding.Da31Binding

class DA31Fragment : Fragment() {
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
  }

  override fun onDestroyView() {
    _binding = null
    super.onDestroyView()
  }
}