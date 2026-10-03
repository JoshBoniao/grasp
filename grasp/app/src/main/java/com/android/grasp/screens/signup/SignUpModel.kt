package com.android.grasp.screens.signup

import com.android.grasp.app.GraspApp
import com.android.grasp.data.FlashcardRepository

class SignUpModel(val app: GraspApp) {

    private val repository = FlashcardRepository(app)

    fun hasAccount(): Boolean = repository.hasAccount()

    fun saveAccount(username: String, password: String) {
        repository.saveUsername(username)
        repository.savePassword(password)
    }
}
