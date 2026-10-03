package com.android.grasp.screens.flashcardlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.grasp.R
import com.android.grasp.data.Flashcard

class FlashcardAdapter(
    private var cards: List<Flashcard>,
    private val onEdit: (Flashcard) -> Unit,
    private val onDelete: (Flashcard) -> Unit
) : RecyclerView.Adapter<FlashcardAdapter.CardViewHolder>() {

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textQuestion: TextView = itemView.findViewById(R.id.textViewQuestion)
        val textAnswer: TextView   = itemView.findViewById(R.id.textViewAnswer)
        val buttonEdit: Button     = itemView.findViewById(R.id.buttonEdit)
        val buttonDelete: Button   = itemView.findViewById(R.id.buttonDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_flashcard, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = cards[position]
        holder.textQuestion.text = card.question
        holder.textAnswer.text   = card.answer
        holder.buttonEdit.setOnClickListener   { onEdit(card) }
        holder.buttonDelete.setOnClickListener { onDelete(card) }
    }

    override fun getItemCount() = cards.size

    fun updateCards(newCards: List<Flashcard>) {
        cards = newCards
        notifyDataSetChanged()
    }
}
