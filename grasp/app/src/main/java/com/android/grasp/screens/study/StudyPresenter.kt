package com.android.grasp.screens.study

class StudyPresenter(
    val view: StudyContract.View,
    val model: StudyModel
) : StudyContract.Presenter {

    private val cards by lazy { model.getAllCards() }
    private var currentIndex = 0
    private var isFlipped    = false
    private var correctCount = 0
    private var answeredCount = 0

    override fun onStart() {
        if (cards.isEmpty()) {
            view.navigateBack()
            return
        }
        showCurrentCard()
    }

    override fun onFlipClicked() {
        isFlipped = !isFlipped
        showCurrentCard()
    }

    override fun onNextClicked() {
        currentIndex = (currentIndex + 1) % cards.size
        isFlipped = false
        showCurrentCard()
    }

    override fun onPrevClicked() {
        currentIndex = (currentIndex - 1 + cards.size) % cards.size
        isFlipped = false
        showCurrentCard()
    }

    override fun onSwipedRight() {
        correctCount++
        answeredCount++
        advanceOrFinish()
    }

    override fun onSwipedLeft() {
        answeredCount++
        advanceOrFinish()
    }

    override fun onExitClicked() {
        view.navigateBack()
    }

    private fun advanceOrFinish() {
        if (answeredCount >= cards.size) {
            view.showResult(correctCount, cards.size)
        } else {
            currentIndex = (currentIndex + 1) % cards.size
            isFlipped = false
            showCurrentCard()
        }
    }

    private fun showCurrentCard() {
        val card = cards[currentIndex]
        view.showCard(card.question, card.answer, isFlipped)
        view.showProgress(currentIndex + 1, cards.size)
        view.updateFlipButton(isFlipped)
        view.showSwipeHint(isFlipped)
    }
}
