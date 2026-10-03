package com.android.grasp.screens.login

class LoginPresenter(
    val view: LoginContract.View,
    val model: LoginModel
) : LoginContract.Presenter {

    override fun onLoginClicked(username: String, password: String) {
        if (username.isBlank() || password.isBlank()) {
            view.showError("Please fill in all fields")
            return
        }
        if (!model.validateCredentials(username, password)) {
            view.showError("Incorrect username or password")
            return
        }
        view.navigateToFlashcardList()
    }

    override fun onSignUpLinkClicked() {
        view.navigateToSignUp()
    }
}
