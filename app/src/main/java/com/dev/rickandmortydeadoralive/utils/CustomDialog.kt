package com.dev.rickandmortydeadoralive.utils

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.dev.rickandmortydeadoralive.R
import kotlinx.android.synthetic.main.custom_dialog.*

class CustomDialog(
    val activity: Activity,
    val gifId: Int,
    val title: String,
    val message: String,
    val listener: CustomDialogClickListener
) {

    fun showDialog() {

        val dialog = Dialog(activity)

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_dialog)

        dialog.gifImageView.setImageResource(gifId)
        dialog.title.text = title
        dialog.message.text = message

        dialog.positiveBtn.setOnClickListener {
            listener.onButtonClickListener()
            dialog.dismiss()
        }

        dialog.show()
    }

}

interface CustomDialogClickListener {

    fun onButtonClickListener()
}