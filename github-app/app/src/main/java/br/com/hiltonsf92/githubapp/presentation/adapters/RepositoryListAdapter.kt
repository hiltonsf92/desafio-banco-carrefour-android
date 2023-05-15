package br.com.hiltonsf92.githubapp.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.hiltonsf92.githubapp.databinding.RepositoryListItemBinding
import br.com.hiltonsf92.githubapp.domain.entities.Repository
import br.com.hiltonsf92.githubapp.presentation.shared.AdapterListener

class RepositoryListAdapter(
    val mListener: AdapterListener<Repository>
) : RecyclerView.Adapter<RepositoryListAdapter.RepositoryListViewHolder>() {

    var repoList: List<Repository> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(newRepoList) {
            field = newRepoList
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryListViewHolder {
        val binding =
            RepositoryListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepositoryListViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: RepositoryListViewHolder, position: Int) {
        viewHolder.bind(repoList[position])
    }

    override fun getItemCount(): Int = repoList.size

    inner class RepositoryListViewHolder(private val binding: RepositoryListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(repo: Repository) {
            binding.nameTextView.text = repo.name
            binding.visibilityChip.text = repo.visibility
            binding.languageTextView.text = repo.language
            binding.cardView.setOnClickListener {
                mListener.performItemClicked(repo)
            }
        }
    }
}