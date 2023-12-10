package com.example.dietasapp.UI

import android.app.AlertDialog
import android.content.Context
import android.widget.LinearLayout
import android.widget.ProgressBar

class DialogProgressBar {
    var builder: AlertDialog.Builder? = null
    var progressDialog: AlertDialog? = null

    constructor(context: Context, string: String){
        progressDialog = getDialogProgressBar(context, string)?.create();
    }

    private fun getDialogProgressBar(context: Context, string: String): AlertDialog.Builder? {
        if (builder == null) {
            builder = AlertDialog.Builder(context)
            builder!!.setTitle(string)
            val progressBar = ProgressBar(context)
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
            )
            progressBar.layoutParams = lp
            builder!!.setView(progressBar)
        }
        return builder
    }
}