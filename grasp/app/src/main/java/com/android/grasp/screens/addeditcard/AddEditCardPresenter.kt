package com.android.grasp.screens.addeditcard

class AddEditCardPresenter(
    val view: AddEditCardContract.View,
    val model: AddEditCardModel,
    private val editCardId: Long?
) : AddEditCardContract.Presenter {

    override fun onSaveClicked(question: String, answer: String) {
        if (question.isBlank() || answer.isBlank()) {
            view.showError("Please fill in both fields")
            return
        }
        if (editCardId != null) {
            model.updateCard(editCardId, question, answer)
        } else {
            model.addCard(question, answer)
        }
        view.closeScreen()
    }
}
