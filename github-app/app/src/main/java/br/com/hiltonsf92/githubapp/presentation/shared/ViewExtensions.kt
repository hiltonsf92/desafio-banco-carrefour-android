package br.com.hiltonsf92.githubapp.presentation.shared

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.fragment.app.Fragment

fun View.hideKeyboard() {
    val imm: InputMethodManager? =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(windowToken, 0)
}

fun ImageView.loadFromUrl(url: String) {
    GlideInstance
        .instance
        .load(url)
        .into(this)
}

fun Fragment.openUrlWithBrowser(url: String?) {
    Intent().apply {
        data = Uri.parse(url)
        action = Intent.ACTION_VIEW
    }.also {
        activity?.startActivity(it)
    }
}