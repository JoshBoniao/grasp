package com.android.grasp.screens.addeditcard

interface AddEditCardContract {

    interface View {
        fun showError(message: String)
        fun closeScreen()
        fun setScreenTitle(isEditing: Boolean)
        fun setSaveButtonLabel(isEditing: Boolean)
    }

    interface Presenter {
        fun onSaveClicked(question: String, answer: String)
    }
}
