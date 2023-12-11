package com.example.dietasapp.UI

import android.app.AlertDialog
import android.content.Context
import android.widget.LinearLayout
import android.widget.ProgressBar
/**
 * Custom dialog for displaying a progress bar with a specified message.
 *
 * @property builder The AlertDialog.Builder used to create the dialog.
 * @property progressDialog The instance of AlertDialog representing the progress dialog.
 * @constructor Creates a DialogProgressBar with the given [context] and [message].
 * @param context The context in which the dialog is created.
 * @param message The message to be displayed alongside the progress bar.
 */
class DialogProgressBar(context: Context, message: String) {
    var builder: AlertDialog.Builder? = null
    var progressDialog: AlertDialog? = null

    init {
        progressDialog = getDialogProgressBar(context, message)?.create()
    }

    /**
     * Gets the AlertDialog.Builder for the progress dialog.
     *
     * @param context The context in which the dialog is created.
     * @param message The message to be displayed alongside the progress bar.
     * @return The configured AlertDialog.Builder.
     */
    private fun getDialogProgressBar(context: Context, message: String): AlertDialog.Builder? {
        if (builder == null) {
            builder = AlertDialog.Builder(context)
            builder!!.setTitle(message)
            val progressBar = ProgressBar(context)
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            progressBar.layoutParams = lp
            builder!!.setView(progressBar)
        }
        return builder
    }
}