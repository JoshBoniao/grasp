package com.android.grasp.data

import android.content.Context
import android.content.SharedPreferences
import org.json.JSONArray
import org.json.JSONObject

class FlashcardRepository(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("grasp_prefs", Context.MODE_PRIVATE)

    fun getAllCards(): MutableList<Flashcard> {
        val json = prefs.getString("cards", null) ?: return defaultCards()
        val array = JSONArray(json)
        val list = mutableListOf<Flashcard>()
        for (i in 0 until array.length()) {
            val obj = array.getJSONObject(i)
            list.add(Flashcard(obj.getLong("id"), obj.getString("question"), obj.getString("answer")))
        }
        return list
    }

    fun saveAllCards(cards: List<Flashcard>) {
        val array = JSONArray()
        cards.forEach { card ->
            val obj = JSONObject()
            obj.put("id", card.id)
            obj.put("question", card.question)
            obj.put("answer", card.answer)
            array.put(obj)
        }
        prefs.edit().putString("cards", array.toString()).apply()
    }

    fun addCard(question: String, answer: String) {
        val cards = getAllCards()
        cards.add(Flashcard(System.currentTimeMillis(), question, answer))
        saveAllCards(cards)
    }

    fun updateCard(id: Long, question: String, answer: String) {
        val cards = getAllCards()
        val index = cards.indexOfFirst { it.id == id }
        if (index != -1) {
            cards[index] = Flashcard(id, question, answer)
            saveAllCards(cards)
        }
    }

    fun deleteCard(id: Long) {
        val cards = getAllCards().filter { it.id != id }
        saveAllCards(cards)
    }

    fun saveUsername(username: String) = prefs.edit().putString("username", username).apply()
    fun getUsername(): String? = prefs.getString("username", null)

    fun savePassword(password: String) = prefs.edit().putString("password", password).apply()
    fun getPassword(): String? = prefs.getString("password", null)

    fun hasAccount(): Boolean = getUsername() != null && getPassword() != null

    fun clearSession() = prefs.edit().remove("username").apply()

    private fun defaultCards(): MutableList<Flashcard> {
        val defaults = mutableListOf(
            Flashcard(1L, "Who is the best philosopher?", "Marcus Aurelius"),
            Flashcard(2L, "What is the Socratic method?", "A form of dialogue-based inquiry used to stimulate critical thinking"),
            Flashcard(3L, "What does Cogito, ergo sum mean?", "I think, therefore I am — Descartes' famous proof of existence")
        )
        saveAllCards(defaults)
        return defaults
    }
}
