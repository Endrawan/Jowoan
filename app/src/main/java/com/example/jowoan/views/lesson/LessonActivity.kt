package com.example.jowoan.views.lesson

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.example.jowoan.R
import com.example.jowoan.adapters.LessonAdapter
import com.example.jowoan.custom.AppCompatActivity
import com.example.jowoan.models.lesson.Lesson
import com.example.jowoan.network.APICallback
import com.example.jowoan.views.auth.LoginActivity
import com.example.jowoan.views.main.MainActivity
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.StackFrom
import kotlinx.android.synthetic.main.activity_lesson.*

class LessonActivity : AppCompatActivity() {

    private val TAG = "LessonActivity"
    val lessons = mutableListOf<Lesson>()
    private lateinit var adapter: LessonAdapter
    private var progressIncrement = 0
    private var currentLesson = 0
    private var questionAnswered = false

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lesson)

        loadLessonsFromAPI()

        cardStackView.layoutManager = CardStackLayoutManager(this).apply {
            setStackFrom(StackFrom.Top)
            setVisibleCount(3)
            setCanScrollHorizontal(false)
            setCanScrollVertical(false)
            setScaleInterval(0.90f)
            setTranslationInterval(12.0f)
        }
        adapter = LessonAdapter(lessons, this, object : LessonAdapter.Action {
            override fun showCorrectDisplay(
                correctionTitle: String?,
                correctWord: String?,
                correction: String?
            ) {
                this@LessonActivity.showCorrectDisplay(correctionTitle, correctWord, correction)
            }

            override fun showWrongDisplay(
                correctionTitle: String?,
                correctWord: String?,
                correction: String?
            ) {
                this@LessonActivity.showWrongDisplay(correctionTitle, correctWord, correction)
            }

            override fun hideSolutionDisplay() {
                this@LessonActivity.hideSolutionDisplay()
            }

            override fun questionAnswered() {
                setQuestionToAnswered()
            }

        })
        cardStackView.adapter = adapter

        closed_lesson.setOnClickListener {
            Intent(applicationContext, MainActivity::class.java).also {
                startActivity(it)
                finishAffinity()
            }
        }

        swipe.setOnClickListener {
            swipeAction()
        }
    }

    private fun loadLessonsFromAPI() {
        val subpracticeID = intent.getIntExtra("SubpracticeID", 0)
        jowoanService.lessonGet(user.token, subpracticeID.toString())
            .enqueue(APICallback(object : APICallback.Action<List<Lesson>> {
                override fun responseSuccess(data: List<Lesson>) {
                    progress.visibility = View.GONE
                    lessons.addAll(data)
                    adapter.notifyDataSetChanged()
                    if (data.isNotEmpty()) {
                        progressIncrement = 100 / data.size
                        adapter.getLessonActionFromPosition(currentLesson)?.onViewShowed()
                    } else {
                        toast("Soal masih belum disediakan coba beberapa saat nanti!")
                        finish()
                    }
                }

                override fun dataNotFound(message: String) {
                    toast("Data Not Found")
                }

                override fun responseFailed(status: String, message: String) {
                    toast("Request gagal. status:$status, message:$message")
                }

                override fun networkFailed(t: Throwable) {
                    toast("Request gagal. error:${t.message}")
                }

                override fun tokenExpired() {
                    toast("Token telah expired, silahkan login ulang")
                    logout()
                    Intent(this@LessonActivity, LoginActivity::class.java).also {
                        startActivity(it)
                    }
                }
            }))
    }

    private fun showCorrectionDisplay(
        correctionTitle: String?,
        correctWord: String?,
        correction: String?
    ) {
        view_correction.visibility = View.VISIBLE
        val textViewTitleCorrection =
            view_correction.findViewById<TextView>(R.id.textView_titleCorrection)
        val textViewCorrectWord = view_correction.findViewById<TextView>(R.id.textView_correctWord)
        val textViewCorrection = view_correction.findViewById<TextView>(R.id.textView_correction)
        if (!correctionTitle.isNullOrEmpty()) {
            textViewTitleCorrection.visibility = View.VISIBLE
            textViewTitleCorrection.text = correctionTitle
        } else {
            textViewTitleCorrection.visibility = View.GONE
        }

        if (!correctWord.isNullOrEmpty()) {
            textViewCorrectWord.visibility = View.VISIBLE
            textViewCorrectWord.text = correctWord
        } else {
            textViewCorrectWord.visibility = View.GONE
        }

        if (!correction.isNullOrEmpty()) {
            textViewCorrection.visibility = View.VISIBLE
            textViewCorrection.text = correction
        } else {
            textViewCorrection.visibility = View.GONE
        }
    }

    private fun showCorrectDisplay(
        correctionTitle: String?,
        correctWord: String?,
        correction: String?
    ) {
        view_correct_label.visibility = View.VISIBLE
        showCorrectionDisplay(correctionTitle, correctWord, correction)
    }

    private fun showWrongDisplay(
        correctionTitle: String?,
        correctWord: String?,
        correction: String?
    ) {
        view_wrong_label.visibility = View.VISIBLE
        showCorrectionDisplay(correctionTitle, correctWord, correction)
    }

    private fun hideSolutionDisplay() {
        view_correction.visibility = View.GONE
        view_correct_label.visibility = View.GONE
        view_wrong_label.visibility = View.GONE
    }

    private fun swipeAction() {
        Log.d(TAG, "Position: $currentLesson, Status: $questionAnswered")
        if (questionAnswered) {
            currentLesson++
            hideSolutionDisplay()
            cardStackView.swipe()
            progress_soal.incrementProgressBy(progressIncrement)
            resetQuestionStatus()
            adapter.getLessonActionFromPosition(currentLesson)?.onViewShowed()
        } else {
            toast("Tolong jawab terlebih dahulu!")
        }
    }

    private fun setQuestionToAnswered() {
        questionAnswered = true
    }

    private fun resetQuestionStatus() {
        questionAnswered = false
    }
}