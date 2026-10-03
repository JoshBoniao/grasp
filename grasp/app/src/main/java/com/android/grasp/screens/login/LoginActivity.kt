package com.android.grasp.screens.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.grasp.R
import com.android.grasp.app.GraspApp
import com.android.grasp.screens.flashcardlist.FlashcardListActivity
import com.android.grasp.screens.signup.SignUpActivity
import com.android.grasp.utils.getEditTextValue
import com.android.grasp.utils.hideView

class LoginActivity : AppCompatActivity(), LoginContract.View {

    lateinit var loginPresenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginPresenter = LoginPresenter(this, LoginModel(application as GraspApp))

        findViewById<Button>(R.id.buttonLogin).setOnClickListener {
            hideView(R.id.textViewError)
            loginPresenter.onLoginClicked(
                getEditTextValue(R.id.editTextUsername),
                getEditTextValue(R.id.editTextPassword)
            )
        }

        findViewById<TextView>(R.id.textViewGoToSignUp).setOnClickListener {
            loginPresenter.onSignUpLinkClicked()
        }
    }

    override fun showError(message: String) {
        findViewById<TextView>(R.id.textViewError).apply {
            text = message
            visibility = View.VISIBLE
        }
    }

    override fun navigateToFlashcardList() {
        startActivity(Intent(this, FlashcardListActivity::class.java))
        finish()
    }

    override fun navigateToSignUp() {
        startActivity(Intent(this, SignUpActivity::class.java))
        finish()
    }
}
