package com.android.grasp.screens.signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.grasp.R
import com.android.grasp.app.GraspApp
import com.android.grasp.screens.flashcardlist.FlashcardListActivity
import com.android.grasp.screens.login.LoginActivity
import com.android.grasp.utils.getEditTextValue
import com.android.grasp.utils.hideView

class SignUpActivity : AppCompatActivity(), SignUpContract.View {

    lateinit var signUpPresenter: SignUpPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        signUpPresenter = SignUpPresenter(this, SignUpModel(application as GraspApp))
        signUpPresenter.onStart()

        findViewById<Button>(R.id.buttonSignUp).setOnClickListener {
            hideView(R.id.textViewError)
            signUpPresenter.onSignUpClicked(
                getEditTextValue(R.id.editTextUsername),
                getEditTextValue(R.id.editTextPassword),
                getEditTextValue(R.id.editTextConfirmPassword)
            )
        }

        findViewById<TextView>(R.id.textViewGoToLogin).setOnClickListener {
            signUpPresenter.onLoginLinkClicked()
        }
    }

    override fun showError(message: String) {
        findViewById<TextView>(R.id.textViewError).apply {
            text = message
            visibility = View.VISIBLE
        }
    }

    override fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
