package com.android.grasp.screens.addeditcard

import com.android.grasp.app.GraspApp
import com.android.grasp.data.FlashcardRepository

class AddEditCardModel(val app: GraspApp) {

    private val repository = FlashcardRepository(app)

    fun addCard(question: String, answer: String) {
        repository.addCard(question, answer)
    }

    fun updateCard(id: Long, question: String, answer: String) {
        repository.updateCard(id, question, answer)
    }
}
