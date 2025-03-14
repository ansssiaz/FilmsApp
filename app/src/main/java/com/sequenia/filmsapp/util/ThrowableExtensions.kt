package com.sequenia.filmsapp.util

import android.content.Context
import com.sequenia.filmsapp.R
import okio.IOException

fun Throwable.getErrorText(context: Context): String = when (this) {
    is IOException -> context.getString(R.string.network_error)
    else -> context.getString(R.string.unknown_error)
}