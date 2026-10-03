package com.android.grasp.screens.login

import com.android.grasp.app.GraspApp
import com.android.grasp.data.FlashcardRepository

class LoginModel(val app: GraspApp) {

    private val repository = FlashcardRepository(app)

    fun validateCredentials(username: String, password: String): Boolean {
        return username == repository.getUsername() && password == repository.getPassword()
    }

    fun hasAccount(): Boolean = repository.hasAccount()

    fun getUsername(): String? = repository.getUsername()
}
