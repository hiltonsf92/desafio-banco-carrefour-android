package br.com.hiltonsf92.githubapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.hiltonsf92.githubapp.databinding.FragmentUserDetailBinding
import br.com.hiltonsf92.githubapp.domain.entities.Repository
import br.com.hiltonsf92.githubapp.domain.entities.UserData
import br.com.hiltonsf92.githubapp.presentation.adapters.RepositoryListAdapter
import br.com.hiltonsf92.githubapp.presentation.shared.AdapterListener
import br.com.hiltonsf92.githubapp.presentation.shared.ErrorInfoBottomSheet
import br.com.hiltonsf92.githubapp.presentation.shared.ErrorInfoBottomSheet.Companion.CLOSE_ACTION
import br.com.hiltonsf92.githubapp.presentation.shared.ErrorInfoBottomSheetAction
import br.com.hiltonsf92.githubapp.presentation.shared.openUrlWithBrowser
import br.com.hiltonsf92.githubapp.presentation.viewmodels.UserDetailViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserDetailFragment : Fragment(), AdapterListener<Repository>, ErrorInfoBottomSheetAction {
    private var _binding: FragmentUserDetailBinding? = null
    private val binding get() = _binding!!

    private val userDetailViewModel: UserDetailViewModel by viewModel()

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
        setupListeners()
        setupObservers()
        loadUserData()
    }

    private fun setupListeners() {
        binding.userInfoCard.setButtonClickListener {
            openUrlWithBrowser(it)
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                userDetailViewModel.userStateFlow.collect { state ->
                    state.handle(
                        loading = { binding.circularProgressIndicator.show() },
                        success = { handleSuccess(it) },
                        error = { handleError(it) }
                    )
                }
            }
        }
    }

    private fun loadUserData() {
        arguments?.getString(LOGIN_KEY)?.let {
            userDetailViewModel.getUserData(it)
            binding.contentLinearLayout.visibility = View.GONE
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

    override fun onAction(action: String) {
        when (action) {
            CLOSE_ACTION -> findNavController().popBackStack()
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