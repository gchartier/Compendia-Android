package com.gabrieldchartier.compendia.util

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import com.afollestad.materialdialogs.MaterialDialog
import com.gabrieldchartier.compendia.R

fun Context.displayToast(@StringRes message: Int) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.displayToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.displaySuccessDialog(message: String?) {
    MaterialDialog(this).show {
        title(R.string.dialog_success_header)
        message(text = message)
        positiveButton(R.string.dialog_confirm)
    }
}

fun Context.displayErrorDialog(message: String?) {
    MaterialDialog(this).show {
        title(R.string.dialog_error_header)
        message(text = message)
        positiveButton(R.string.dialog_confirm)
    }
}