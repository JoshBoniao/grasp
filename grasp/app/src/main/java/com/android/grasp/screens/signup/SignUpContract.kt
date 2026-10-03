package com.android.grasp.screens.signup

interface SignUpContract {

    interface View {
        fun showError(message: String)
        fun navigateToLogin()
    }

    interface Presenter {
        fun onStart()
        fun onSignUpClicked(username: String, password: String, confirmPassword: String)
        fun onLoginLinkClicked()
    }
}
