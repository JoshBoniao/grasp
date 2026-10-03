package com.android.grasp.screens.flashcardlist

import com.android.grasp.data.Flashcard

interface FlashcardListContract {

    interface View {
        fun showCards(cards: List<Flashcard>)
        fun showCardCount(count: Int, studyMinutes: Int)
        fun showWelcome(username: String)
        fun navigateToAddCard()
        fun navigateToEditCard(card: Flashcard)
        fun navigateToStudy()
        fun navigateToLogin()
    }

    interface Presenter {
        fun loadData()
        fun onAddCardClicked()
        fun onEditCardClicked(card: Flashcard)
        fun onDeleteCardClicked(card: Flashcard)
        fun onStudyClicked()
        fun onLogoutClicked()
    }
}
