package br.com.hiltonsf92.githubapp.presentation.shared

interface AdapterListener<T> {
    fun performItemClicked(value: T)
}