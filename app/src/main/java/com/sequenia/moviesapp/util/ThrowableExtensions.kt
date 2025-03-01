package com.sequenia.moviesapp.util

import android.content.Context
import com.sequenia.moviesapp.R
import okio.IOException

fun Throwable.getErrorText(context: Context): String = when (this) {
    is IOException -> context.getString(R.string.network_error)
    else -> context.getString(R.string.unknown_error)
}