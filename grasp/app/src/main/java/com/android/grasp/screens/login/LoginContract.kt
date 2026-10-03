package com.android.grasp.screens.login

interface LoginContract {

    interface View {
        fun showError(message: String)
        fun navigateToFlashcardList()
        fun navigateToSignUp()
    }

    interface Presenter {
        fun onLoginClicked(username: String, password: String)
        fun onSignUpLinkClicked()
    }
}
