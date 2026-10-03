package com.android.grasp.screens.study

interface StudyContract {

    interface View {
        fun showCard(question: String, answer: String, isFlipped: Boolean)
        fun showProgress(current: Int, total: Int)
        fun updateFlipButton(isFlipped: Boolean)
        fun showSwipeHint(isFlipped: Boolean)
        fun showResult(correct: Int, total: Int)
        fun navigateBack()
    }

    interface Presenter {
        fun onStart()
        fun onFlipClicked()
        fun onNextClicked()
        fun onPrevClicked()
        fun onSwipedRight()
        fun onSwipedLeft()
        fun onExitClicked()
    }
}
