package br.com.hiltonsf92.githubapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.hiltonsf92.githubapp.R
import br.com.hiltonsf92.githubapp.databinding.FragmentUserDetailBinding
import br.com.hiltonsf92.githubapp.domain.entities.Repository
import br.com.hiltonsf92.githubapp.domain.entities.UserData
import br.com.hiltonsf92.githubapp.presentation.adapters.RepositoryListAdapter
import br.com.hiltonsf92.githubapp.presentation.shared.AdapterListener
import br.com.hiltonsf92.githubapp.presentation.shared.ErrorInfoBottomSheet
import br.com.hiltonsf92.githubapp.presentation.shared.ErrorInfoBottomSheetHandler
import br.com.hiltonsf92.githubapp.presentation.shared.hideKeyboard
import br.com.hiltonsf92.githubapp.presentation.shared.openUrlWithBrowser
import br.com.hiltonsf92.githubapp.presentation.viewmodels.UserDetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserDetailFragment : Fragment(), AdapterListener<Repository>, ErrorInfoBottomSheetHandler {
    private var _binding: FragmentUserDetailBinding? = null
    private val binding get() = _binding!!

    private val userDetailViewModel: UserDetailViewModel by viewModel()

    private var login: String? = null

    private val mAdapter: RepositoryListAdapter by lazy { RepositoryListAdapter(this) }

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
        binding.contentLinearLayout.visibility = View.GONE
        arguments?.getString(LOGIN_KEY)?.let {
            loadUserData(it)
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
            loadUserData(binding.searchTextField.text.toString())
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

    private fun loadUserData(login: String) {
        if (userDetailViewModel.shouldRequestAgain(login)) {
            binding.contentLinearLayout.visibility = View.GONE
            userDetailViewModel.getUserByLogin(login)
        }
    }

    private fun handleSuccess(data: UserData) {
        binding.contentLinearLayout.visibility = View.VISIBLE
        binding.userInfoCard.fillWithFullUser(data.user)
        setupRepoListAdapter(data.repositories)
        binding.circularProgressIndicator.hide()
    }

    private fun handleError(exception: Exception) {
        val bottomSheet = ErrorInfoBottomSheet.newInstance(exception.message)
        bottomSheet.show(childFragmentManager, ErrorInfoBottomSheet.TAG)
        binding.contentLinearLayout.visibility = View.GONE
        binding.circularProgressIndicator.hide()
    }

    private fun setupRepoListAdapter(repoList: List<Repository>) {
        repoList.takeUnless { it.isEmpty() }?.run {
            mAdapter.repoList = this
            binding.reposListRecyclerView.apply {
                adapter = mAdapter
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
            }.also {
                binding.titleTextView.visibility = View.VISIBLE
                binding.reposListRecyclerView.visibility = View.VISIBLE
                binding.circularProgressIndicator.hide()
            }
        } ?: also {
            binding.titleTextView.visibility = View.GONE
            binding.reposListRecyclerView.visibility = View.GONE
            binding.circularProgressIndicator.hide()
        }
    }

    override fun retry() {
        login?.let {
            loadUserData(it)
        }
    }

    override fun performItemClicked(value: Repository) {
        openUrlWithBrowser(value.url)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}