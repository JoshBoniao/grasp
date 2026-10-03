package com.android.grasp.screens.study

import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.android.grasp.R
import com.android.grasp.app.GraspApp
import kotlin.math.abs

class StudyActivity : AppCompatActivity(), StudyContract.View {

    lateinit var studyPresenter: StudyPresenter

    private lateinit var textViewBadge: TextView
    private lateinit var textViewContent: TextView
    private lateinit var textViewTapHint: TextView
    private lateinit var textViewProgress: TextView
    private lateinit var textViewSwipeHint: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var buttonFlip: Button
    private lateinit var buttonNext: Button
    private lateinit var buttonPrev: Button
    private lateinit var cardContainer: View

    private var swipeTouchStartX = 0f
    private var swipeTouchStartY = 0f
    private val SWIPE_THRESHOLD  = 120f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_study)

        studyPresenter = StudyPresenter(this, StudyModel(application as GraspApp))

        textViewBadge     = findViewById(R.id.textViewCardBadge)
        textViewContent   = findViewById(R.id.textViewCardContent)
        textViewTapHint   = findViewById(R.id.textViewTapHint)
        textViewProgress  = findViewById(R.id.textViewProgress)
        textViewSwipeHint = findViewById(R.id.textViewSwipeHint)
        progressBar       = findViewById(R.id.progressBarStudy)
        buttonFlip        = findViewById(R.id.buttonFlip)
        buttonNext        = findViewById(R.id.buttonNext)
        buttonPrev        = findViewById(R.id.buttonPrev)
        cardContainer     = findViewById(R.id.cardContainer)

        cardContainer.setOnTouchListener { v, event -> handleCardTouch(v, event) }
        buttonFlip.setOnClickListener  { studyPresenter.onFlipClicked() }
        buttonNext.setOnClickListener  { studyPresenter.onNextClicked() }
        buttonPrev.setOnClickListener  { studyPresenter.onPrevClicked() }
        findViewById<Button>(R.id.buttonExitStudy).setOnClickListener { studyPresenter.onExitClicked() }

        studyPresenter.onStart()
    }


    private fun handleCardTouch(v: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                swipeTouchStartX = event.x
                swipeTouchStartY = event.y
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = event.x - swipeTouchStartX
                cardContainer.translationX = dx * 0.4f
                cardContainer.setBackgroundResource(
                    when {
                        dx > 0  -> R.drawable.bg_card_correct
                        dx < 0  -> R.drawable.bg_card_wrong
                        else    -> R.drawable.bg_card
                    }
                )
                return true
            }
            MotionEvent.ACTION_UP -> {
                val dx = event.x - swipeTouchStartX
                val dy = event.y - swipeTouchStartY
                cardContainer.setBackgroundResource(R.drawable.bg_card)

                if (abs(dx) > SWIPE_THRESHOLD && abs(dx) > abs(dy)) {
                    val direction = if (dx > 0) 1f else -1f
                    animateCardOffScreen(direction) {
                        cardContainer.translationX = 0f
                        if (direction > 0) studyPresenter.onSwipedRight()
                        else               studyPresenter.onSwipedLeft()
                    }
                } else {
                    animateSnapBack()
                    if (abs(dx) < 10f && abs(dy) < 10f) studyPresenter.onFlipClicked()
                }
                return true
            }
            MotionEvent.ACTION_CANCEL -> {
                cardContainer.translationX = 0f
                cardContainer.setBackgroundResource(R.drawable.bg_card)
                return true
            }
        }
        return false
    }

    private fun animateCardOffScreen(direction: Float, onEnd: () -> Unit) {
        val target = direction * resources.displayMetrics.widthPixels * 1.5f
        ObjectAnimator.ofFloat(cardContainer, "translationX", target).apply {
            duration = 250
            addListener(object : android.animation.AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: android.animation.Animator) {
                    onEnd()
                    cardContainer.translationX = -target
                    ObjectAnimator.ofFloat(cardContainer, "translationX", 0f)
                        .apply { duration = 220 }.start()
                }
            })
            start()
        }
    }

    private fun animateSnapBack() {
        ObjectAnimator.ofFloat(cardContainer, "translationX", 0f).apply { duration = 200 }.start()
    }


    override fun showCard(question: String, answer: String, isFlipped: Boolean) {
        if (isFlipped) {
            textViewBadge.setText(R.string.label_answer_badge)
            textViewBadge.setTextColor(ContextCompat.getColor(this, R.color.answer_highlight))
            textViewContent.text = answer
            textViewTapHint.setText(R.string.tap_to_hide)
        } else {
            textViewBadge.setText(R.string.label_question_badge)
            textViewBadge.setTextColor(ContextCompat.getColor(this, R.color.text_hint))
            textViewContent.text = question
            textViewTapHint.setText(R.string.tap_to_reveal)
        }
    }

    override fun showProgress(current: Int, total: Int) {
        textViewProgress.text = "$current / $total"
        progressBar.progress  = ((current.toFloat() / total) * 100).toInt()
        buttonNext.isEnabled  = total > 1
        buttonPrev.isEnabled  = total > 1
        buttonNext.alpha      = if (total > 1) 1f else 0.4f
        buttonPrev.alpha      = if (total > 1) 1f else 0.4f
    }

    override fun updateFlipButton(isFlipped: Boolean) {
        buttonFlip.setText(if (isFlipped) R.string.btn_hide_answer else R.string.btn_show_answer)
    }

    override fun showSwipeHint(isFlipped: Boolean) {
        textViewSwipeHint.visibility = if (isFlipped) View.VISIBLE else View.GONE
    }

    override fun showResult(correct: Int, total: Int) {
        AlertDialog.Builder(this)
            .setTitle("Session Complete!")
            .setMessage("You got $correct out of $total correct.\n\nGreat job studying with Grasp!")
            .setPositiveButton("Done")        { _, _ -> finish() }
            .setNegativeButton("Study Again") { _, _ -> recreate() }
            .setCancelable(false)
            .show()
    }

    override fun navigateBack() { finish() }
}
