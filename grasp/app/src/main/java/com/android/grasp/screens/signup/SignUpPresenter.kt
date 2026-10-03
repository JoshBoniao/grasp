package com.android.grasp.screens.signup

class SignUpPresenter(
    val view: SignUpContract.View,
    val model: SignUpModel
) : SignUpContract.Presenter {

    override fun onStart() {
        if (model.hasAccount()) {
            view.navigateToLogin()
        }
    }

    override fun onSignUpClicked(username: String, password: String, confirmPassword: String) {
        if (username.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            view.showError("Please fill in all fields")
            return
        }
        if (username.length < 3) {
            view.showError("Username must be at least 3 characters")
            return
        }
        if (password.length < 4) {
            view.showError("Password must be at least 4 characters")
            return
        }
        if (password != confirmPassword) {
            view.showError("Passwords do not match")
            return
        }
        model.saveAccount(username, password)
        view.navigateToLogin()
    }

    override fun onLoginLinkClicked() {
        view.navigateToLogin()
    }
}
