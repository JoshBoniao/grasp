package com.android.grasp.screens.flashcardlist

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.grasp.R
import com.android.grasp.app.GraspApp
import com.android.grasp.data.Flashcard
import com.android.grasp.screens.addeditcard.AddEditCardActivity
import com.android.grasp.screens.login.LoginActivity
import com.android.grasp.screens.study.StudyActivity

class FlashcardListActivity : AppCompatActivity(), FlashcardListContract.View {

    lateinit var flashcardListPresenter: FlashcardListPresenter
    private lateinit var adapter: FlashcardAdapter
    private lateinit var buttonStudyNow: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flashcard_list)

        flashcardListPresenter = FlashcardListPresenter(
            this,
            FlashcardListModel(application as GraspApp)
        )

        adapter = FlashcardAdapter(
            cards    = emptyList(),
            onEdit   = { card -> flashcardListPresenter.onEditCardClicked(card) },
            onDelete = { card -> confirmDelete(card) }
        )

        findViewById<RecyclerView>(R.id.recyclerViewCards).apply {
            layoutManager = LinearLayoutManager(this@FlashcardListActivity)
            adapter = this@FlashcardListActivity.adapter
        }

        buttonStudyNow = findViewById(R.id.buttonStudyNow)

        findViewById<Button>(R.id.buttonAddCard).setOnClickListener  { flashcardListPresenter.onAddCardClicked() }
        buttonStudyNow.setOnClickListener                             { flashcardListPresenter.onStudyClicked() }
        findViewById<Button>(R.id.buttonLogout).setOnClickListener   { flashcardListPresenter.onLogoutClicked() }
    }

    override fun onResume() {
        super.onResume()
        flashcardListPresenter.loadData()
    }

    // ─── FlashcardListContract.View ─────────────────────────────────────────

    override fun showCards(cards: List<Flashcard>) {
        adapter.updateCards(cards)
        val empty = cards.isEmpty()
        findViewById<TextView>(R.id.textViewEmpty).visibility          = if (empty) View.VISIBLE else View.GONE
        findViewById<RecyclerView>(R.id.recyclerViewCards).visibility  = if (empty) View.GONE   else View.VISIBLE
        buttonStudyNow.isEnabled = !empty
        buttonStudyNow.alpha     = if (!empty) 1f else 0.4f
    }

    override fun showCardCount(count: Int, studyMinutes: Int) {
        findViewById<TextView>(R.id.textViewCardCount).text = count.toString()
        findViewById<TextView>(R.id.textViewStudyTime).text = studyMinutes.toString()
    }

    override fun showWelcome(username: String) {
        findViewById<TextView>(R.id.textViewWelcome).text = "Welcome back, $username"
    }

    override fun navigateToAddCard() {
        startActivity(Intent(this, AddEditCardActivity::class.java))
    }

    override fun navigateToEditCard(card: Flashcard) {
        startActivity(Intent(this, AddEditCardActivity::class.java).apply {
            putExtra(AddEditCardActivity.EXTRA_CARD_ID,       card.id)
            putExtra(AddEditCardActivity.EXTRA_CARD_QUESTION, card.question)
            putExtra(AddEditCardActivity.EXTRA_CARD_ANSWER,   card.answer)
        })
    }

    override fun navigateToStudy() {
        startActivity(Intent(this, StudyActivity::class.java))
    }

    override fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun confirmDelete(card: Flashcard) {
        AlertDialog.Builder(this)
            .setTitle(R.string.confirm_delete_title)
            .setMessage(R.string.confirm_delete_message)
            .setPositiveButton(R.string.btn_confirm_delete) { _, _ -> flashcardListPresenter.onDeleteCardClicked(card) }
            .setNegativeButton(R.string.btn_cancel, null)
            .show()
    }
}
