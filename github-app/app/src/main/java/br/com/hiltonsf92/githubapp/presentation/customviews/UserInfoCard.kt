package br.com.hiltonsf92.githubapp.presentation.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import br.com.hiltonsf92.githubapp.databinding.LayoutUserInfoCardBinding
import br.com.hiltonsf92.githubapp.domain.entities.User
import br.com.hiltonsf92.githubapp.presentation.shared.loadFromUrl

class UserInfoCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding: LayoutUserInfoCardBinding =
        LayoutUserInfoCardBinding.inflate(LayoutInflater.from(context), this, true)

    fun fillWithUser(user: User) {
        user.run {
            binding.imageView.loadFromUrl(avatarUrl)
            binding.loginTextView.text = "@$login"
            binding.nameTextView.visibility = View.GONE
            binding.locationTextView.visibility = View.GONE
            binding.reposTextView.visibility = View.GONE
        }
    }

    override fun setOnClickListener(listener: OnClickListener?) {
        binding.cardView.setOnClickListener(listener)
    }
}