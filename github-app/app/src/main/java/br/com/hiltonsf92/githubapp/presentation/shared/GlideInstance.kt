package br.com.hiltonsf92.githubapp.presentation.shared

import com.bumptech.glide.RequestManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object GlideInstance : KoinComponent {
    val glide: RequestManager by inject()
}