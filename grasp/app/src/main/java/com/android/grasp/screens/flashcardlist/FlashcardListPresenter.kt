package com.android.grasp.screens.flashcardlist

import com.android.grasp.data.Flashcard
import kotlin.math.ceil

class FlashcardListPresenter(
    val view: FlashcardListContract.View,
    val model: FlashcardListModel
) : FlashcardListContract.Presenter {

    override fun loadData() {
        view.showWelcome(model.getUsername())
        refreshCards()
    }

    override fun onAddCardClicked() {
        view.navigateToAddCard()
    }

    override fun onEditCardClicked(card: Flashcard) {
        view.navigateToEditCard(card)
    }

    override fun onDeleteCardClicked(card: Flashcard) {
        model.deleteCard(card.id)
        refreshCards()
    }

    override fun onStudyClicked() {
        if (model.hasCards()) {
            view.navigateToStudy()
        }
    }

    override fun onLogoutClicked() {
        model.clearSession()
        view.navigateToLogin()
    }

    private fun refreshCards() {
        val cards = model.getAllCards()
        view.showCards(cards)
        view.showCardCount(cards.size, ceil(cards.size * 1.3).toInt())
    }
}
