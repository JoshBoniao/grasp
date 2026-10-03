package com.android.grasp.screens.flashcardlist

import com.android.grasp.app.GraspApp
import com.android.grasp.data.Flashcard
import com.android.grasp.data.FlashcardRepository

class FlashcardListModel(val app: GraspApp) {

    private val repository = FlashcardRepository(app)

    fun getAllCards(): List<Flashcard> = repository.getAllCards()

    fun deleteCard(id: Long) = repository.deleteCard(id)

    fun getUsername(): String = repository.getUsername() ?: "there"

    fun clearSession() = repository.clearSession()

    fun hasCards(): Boolean = repository.getAllCards().isNotEmpty()
}
