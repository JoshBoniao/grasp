package com.android.grasp.utils

import android.app.Activity
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

fun Activity.getEditTextValue(id: Int): String {
    return findViewById<EditText>(id).text.toString()
}


fun Activity.showError(textViewId: Int, message: String) {
    findViewById<TextView>(textViewId).apply {
        text = message
        visibility = View.VISIBLE
    }
}

fun Activity.hideView(id: Int) {
    findViewById<View>(id).visibility = View.GONE
}

