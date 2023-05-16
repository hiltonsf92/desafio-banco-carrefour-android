package br.com.hiltonsf92.githubapp.presentation.shared

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import br.com.hiltonsf92.githubapp.databinding.BottomSheetErrorInfoBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

interface ErrorInfoBottomSheetAction {
    fun onAction(action: String)
}

class ErrorInfoBottomSheet : BottomSheetDialogFragment() {
    private var _binding: BottomSheetErrorInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetErrorInfoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val message = arguments?.getString(MESSAGE) ?: DEFAULT_MESSAGE
        val action = arguments?.getString(ACTION) ?: CLOSE_ACTION
        binding.messageTextView.text = message
        binding.actionButton.setOnClickListener {
            (parentFragment as? ErrorInfoBottomSheetAction)?.onAction(action)
            dismiss()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        const val TAG = "ErrorInfoBottomSheet"
        private const val DEFAULT_MESSAGE = "Error processing request"
        private const val MESSAGE = "message"
        private const val ACTION = "action"
        const val CLOSE_ACTION = "close"

        fun newInstance(message: String? = null, action: String? = null): ErrorInfoBottomSheet =
            ErrorInfoBottomSheet().apply {
                isCancelable = false
                arguments = bundleOf(MESSAGE to message, ACTION to action)
            }
    }
}