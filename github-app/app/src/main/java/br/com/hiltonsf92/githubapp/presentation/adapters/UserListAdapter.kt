package br.com.hiltonsf92.githubapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.hiltonsf92.githubapp.databinding.UserListItemBinding
import br.com.hiltonsf92.githubapp.domain.entities.User
import br.com.hiltonsf92.githubapp.presentation.shared.AdapterListener

class UserListAdapter(
    val mListener: AdapterListener<User>,
    private val userList: List<User>
) : RecyclerView.Adapter<UserListAdapter.UserListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val binding = UserListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserListViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: UserListViewHolder, position: Int) {
        viewHolder.bind(userList[position])
    }

    override fun getItemCount(): Int = userList.size

    inner class UserListViewHolder(private val binding: UserListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.userInfoCard.fillWithUser(user)
            binding.userInfoCard.setOnClickListener {
                mListener.performItemClicked(user)
            }
        }
    }
}