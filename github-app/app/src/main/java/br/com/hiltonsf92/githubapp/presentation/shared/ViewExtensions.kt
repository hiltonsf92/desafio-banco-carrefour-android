package br.com.hiltonsf92.githubapp.presentation.shared

import android.widget.ImageView

fun ImageView.loadFromUrl(url: String) {
    GlideInstance
        .glide
        .load(url)
        .fitCenter()
        .into(this)
}