package br.com.hiltonsf92.githubapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.hiltonsf92.githubapp.R
import br.com.hiltonsf92.githubapp.databinding.FragmentUserListBinding
import br.com.hiltonsf92.githubapp.domain.entities.User
import br.com.hiltonsf92.githubapp.presentation.adapters.UserListAdapter
import br.com.hiltonsf92.githubapp.presentation.shared.AdapterListener
import br.com.hiltonsf92.githubapp.presentation.shared.ErrorInfoBottomSheet
import br.com.hiltonsf92.githubapp.presentation.shared.ErrorInfoBottomSheet.Companion.CLOSE_ACTION
import br.com.hiltonsf92.githubapp.presentation.shared.ErrorInfoBottomSheetAction
import br.com.hiltonsf92.githubapp.presentation.shared.hideKeyboard
import br.com.hiltonsf92.githubapp.presentation.viewmodels.UserListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

const val LOGIN_KEY = "login"

class UserListFragment : Fragment(), AdapterListener<User>, ErrorInfoBottomSheetAction {
    private var _binding: FragmentUserListBinding? = null
    private val binding get() = _binding!!

    private val userListViewModel: UserListViewModel by viewModel()

    private var mAdapter: UserListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.contentLinearLayout.visibility = View.GONE
        setupListeners()
        setupObservers()
        setupUserListAdapter()
        loadUsers()
    }

    private fun setupListeners() {
        binding.searchTextLayout.setEndIconOnClickListener { performSearch() }
        binding.searchTextField.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) performSearch()
            false
        }
    }

    private fun performSearch() {
        if (validate()) {
            userListViewModel.searchUser(binding.searchTextField.text.toString())
            binding.searchTextField.clearFocus()
            binding.root.hideKeyboard()
        }
    }

    private fun validate(): Boolean {
        if (userListViewModel.isInvalidQuery(binding.searchTextField.text.toString())) {
            binding.searchTextLayout.error = getString(R.string.user_detail_search_error)
            return false
        }
        binding.searchTextLayout.error = null
        return true
    }

    private fun setupObservers() {
        userListViewModel.userListState.removeObservers(viewLifecycleOwner)
        userListViewModel.userListState.observe(viewLifecycleOwner) { state ->
            state.handle(
                loading = { binding.circularProgressIndicator.show() },
                success = { handleSuccess(it) },
                error = { handleError(it) }
            )
        }
    }

    private fun handleSuccess(userList: List<User>) {
        mAdapter?.userList = userList
        binding.contentLinearLayout.visibility = View.VISIBLE
        binding.circularProgressIndicator.hide()
    }

    private fun handleError(exception: Exception) {
        val bottomSheet = ErrorInfoBottomSheet.newInstance(exception.message)
        bottomSheet.show(childFragmentManager, ErrorInfoBottomSheet.TAG)
        binding.contentLinearLayout.visibility = View.GONE
        binding.circularProgressIndicator.hide()
    }

    private fun setupUserListAdapter() {
        mAdapter = UserListAdapter(this)
        binding.userListRecyclerView.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }

    private fun loadUsers() {
        binding.searchTextField.setText(userListViewModel.lastQuery)
        if (userListViewModel.shouldRequestAgain()) {
            userListViewModel.getAllUsers()
            binding.contentLinearLayout.visibility = View.GONE
        }
    }

    override fun onAction(action: String) {
        when (action) {
            CLOSE_ACTION -> loadUsers()
        }
    }

    override fun performItemClicked(value: User) {
        val bundle = bundleOf(LOGIN_KEY to value.login)
        findNavController().navigate(R.id.action_userListFragment_to_userDetailFragment, bundle)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}