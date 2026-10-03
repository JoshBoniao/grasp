package com.android.grasp.screens.addeditcard

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.grasp.R
import com.android.grasp.app.GraspApp
import com.android.grasp.utils.getEditTextValue
import com.android.grasp.utils.hideView

class AddEditCardActivity : AppCompatActivity(), AddEditCardContract.View {

    companion object {
        const val EXTRA_CARD_ID       = "extra_card_id"
        const val EXTRA_CARD_QUESTION = "extra_card_question"
        const val EXTRA_CARD_ANSWER   = "extra_card_answer"
    }

    lateinit var addEditCardPresenter: AddEditCardPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_card)

        val editCardId       = if (intent.hasExtra(EXTRA_CARD_ID)) intent.getLongExtra(EXTRA_CARD_ID, -1) else null
        val existingQuestion = intent.getStringExtra(EXTRA_CARD_QUESTION)
        val existingAnswer   = intent.getStringExtra(EXTRA_CARD_ANSWER)
        val isEditing        = editCardId != null

        addEditCardPresenter = AddEditCardPresenter(
            this,
            AddEditCardModel(application as GraspApp),
            editCardId
        )

        setScreenTitle(isEditing)
        setSaveButtonLabel(isEditing)

        if (isEditing) {
            findViewById<EditText>(R.id.editTextQuestion).setText(existingQuestion)
            findViewById<EditText>(R.id.editTextAnswer).setText(existingAnswer)
        }

        findViewById<Button>(R.id.buttonSave).setOnClickListener {
            hideView(R.id.textViewError)
            addEditCardPresenter.onSaveClicked(
                getEditTextValue(R.id.editTextQuestion),
                getEditTextValue(R.id.editTextAnswer)
            )
        }

        findViewById<Button>(R.id.buttonCancel).setOnClickListener { finish() }
        findViewById<Button>(R.id.buttonBack).setOnClickListener   { finish() }
    }

    override fun showError(message: String) {
        findViewById<TextView>(R.id.textViewError).apply {
            text = message
            visibility = View.VISIBLE
        }
    }

    override fun closeScreen() { finish() }

    override fun setScreenTitle(isEditing: Boolean) {
        findViewById<TextView>(R.id.textViewScreenTitle)
            .setText(if (isEditing) R.string.title_edit_card else R.string.title_new_card)
    }

    override fun setSaveButtonLabel(isEditing: Boolean) {
        findViewById<Button>(R.id.buttonSave)
            .setText(if (isEditing) R.string.btn_save_changes else R.string.btn_add_to_cards)
    }
}
