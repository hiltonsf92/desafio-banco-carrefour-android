package br.com.hiltonsf92.githubapp.data.models

import br.com.hiltonsf92.githubapp.domain.entities.Repository
import com.google.gson.annotations.SerializedName

data class RepositoryModel(
    @SerializedName("full_name")
    val name: String,
    val language: String?,
    @SerializedName("html_url")
    val url: String,
    val private: Boolean,
) {
    fun toEntity(): Repository = Repository(
        name = name,
        language = language,
        url = url,
        private = private
    )
}
