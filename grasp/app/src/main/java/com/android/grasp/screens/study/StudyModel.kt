package com.android.grasp.screens.study

import com.android.grasp.app.GraspApp
import com.android.grasp.data.Flashcard
import com.android.grasp.data.FlashcardRepository

class StudyModel(val app: GraspApp) {

    private val repository = FlashcardRepository(app)

    fun getAllCards(): List<Flashcard> = repository.getAllCards()
}
