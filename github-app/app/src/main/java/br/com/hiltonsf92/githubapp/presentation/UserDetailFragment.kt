package br.com.hiltonsf92.githubapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import br.com.hiltonsf92.githubapp.R
import br.com.hiltonsf92.githubapp.databinding.FragmentUserDetailBinding
import br.com.hiltonsf92.githubapp.domain.entities.User
import br.com.hiltonsf92.githubapp.presentation.shared.ErrorInfoBottomSheet
import br.com.hiltonsf92.githubapp.presentation.shared.ErrorInfoBottomSheetHandler
import br.com.hiltonsf92.githubapp.presentation.shared.hideKeyboard
import br.com.hiltonsf92.githubapp.presentation.shared.openUrlWithBrowser
import br.com.hiltonsf92.githubapp.presentation.viewmodels.UserDetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserDetailFragment : Fragment(), ErrorInfoBottomSheetHandler {
    private var _binding: FragmentUserDetailBinding? = null
    private val binding get() = _binding!!

    private val userDetailViewModel: UserDetailViewModel by viewModel()

    private var login: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.userInfoCard.visibility = View.GONE
        arguments?.getString(LOGIN_KEY)?.let {
            userDetailViewModel.getUserByLogin(it)
            login = it
        }
        setupListeners()
        setupObservers()
    }

    private fun setupListeners() {
        binding.searchTextLayout.setEndIconOnClickListener { performSearch() }
        binding.searchTextField.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) performSearch()
            false
        }
        binding.userInfoCard.setButtonClickListener {
            openUrlWithBrowser(it)
        }
    }

    private fun performSearch() {
        if (validate()) {
            userDetailViewModel.getUserByLogin(binding.searchTextField.text.toString())
            login = binding.searchTextField.text.toString()
            binding.searchTextField.text = null
            binding.searchTextField.clearFocus()
            binding.root.hideKeyboard()
        }
    }

    private fun validate(): Boolean {
        if (binding.searchTextField.text?.isEmpty() == true) {
            binding.searchTextLayout.error = getString(R.string.user_detail_search_error)
            return false
        }
        binding.searchTextLayout.error = null
        return true
    }

    private fun setupObservers() {
        userDetailViewModel.userState.removeObservers(this)
        userDetailViewModel.userState.observe(viewLifecycleOwner) { state ->
            state.handle(
                loading = { binding.circularProgressIndicator.show() },
                success = { handleSuccess(it) },
                error = { handleError(it) }
            )
        }
    }

    private fun handleSuccess(user: User) {
        binding.userInfoCard.fillWithFullUser(user)
        binding.userInfoCard.visibility = View.VISIBLE
        binding.circularProgressIndicator.hide()
    }

    private fun handleError(exception: Exception) {
        val bottomSheet = ErrorInfoBottomSheet.newInstance(exception.message)
        bottomSheet.show(childFragmentManager, ErrorInfoBottomSheet.TAG)
        binding.circularProgressIndicator.hide()
    }

    override fun retry() {
        login?.let {
            userDetailViewModel.getUserByLogin(it)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}