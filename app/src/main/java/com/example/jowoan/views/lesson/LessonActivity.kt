package com.example.jowoan.views.lesson

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.example.jowoan.R
import com.example.jowoan.adapters.LessonAdapter
import com.example.jowoan.config.LessonConfig
import com.example.jowoan.custom.AppCompatActivity
import com.example.jowoan.models.Activity
import com.example.jowoan.models.Completion
import com.example.jowoan.models.User
import com.example.jowoan.models.lesson.Lesson
import com.example.jowoan.network.APICallback
import com.example.jowoan.views.auth.LoginActivity
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.StackFrom
import kotlinx.android.synthetic.main.activity_lesson.*
import kotlinx.android.synthetic.main.view_lesson_progress.view.*

class LessonActivity : AppCompatActivity() {

    private val TAG = "LessonActivity"
    val lessons = mutableListOf<Lesson>()
    private lateinit var adapter: LessonAdapter
    private var subpracticeID = 0
    private var totalQuestion = 0
    private var progressIncrement = 0
    private var currentLesson = 0
    private var questionStatus: Int = LessonConfig.ANSWER_HASNT_ANSWERED
    private var resultShowed = false

    private var user_update_done = false
    private var completion_update_done = false
    private var activity_update_done = false

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

            override fun questionAnswered(status: Int) {
                setQuestionToAnswered(status)
            }

            override fun retryNextTime(lesson: Lesson) {
                this@LessonActivity.retryNextTime(lesson)
            }

        })
        cardStackView.adapter = adapter

        closed_lesson.setOnClickListener {
            finish()
        }

        swipe.setOnClickListener {
            swipeAction()
        }
    }

    private fun loadLessonsFromAPI() {
        subpracticeID = intent.getIntExtra("SubpracticeID", 0)
        jowoanService.lessonGet(user.token, subpracticeID.toString())
            .enqueue(APICallback(object : APICallback.Action<List<Lesson>> {
                override fun responseSuccess(data: List<Lesson>) {
                    progress.visibility = View.GONE
                    totalQuestion = data.size
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
        Log.d(TAG, "Position: $currentLesson, Status: $questionStatus")
        if (questionStatus == LessonConfig.ANSWER_CORRECT) progress_soal.incrementProgressBy(
            progressIncrement
        )
        when (questionStatus) {
            LessonConfig.ANSWER_CORRECT, LessonConfig.ANSWER_WRONG -> {
                currentLesson++
                if (resultShowed) {
                    lessonDone()
                }
                if (currentLesson == lessons.size && !resultShowed) {
                    retryNextTime(LessonConfig.resultTemplate)
                    resultShowed = true
                }
                hideSolutionDisplay()
                cardStackView.swipe()
                resetQuestionStatus()
                adapter.getLessonActionFromPosition(currentLesson)?.onViewShowed()
            }
            else -> toast("Tolong jawab terlebih dahulu!")
        }
    }

    private fun setQuestionToAnswered(status: Int) {
        questionStatus = status
    }

    private fun resetQuestionStatus() {
        questionStatus = LessonConfig.ANSWER_HASNT_ANSWERED
    }

    private fun retryNextTime(lesson: Lesson) {
        val lastIndex = lessons.size - 1
        lessons.add(lesson)
        adapter.notifyItemRangeChanged(lastIndex, 1)
    }

    private fun lessonDone() {
        swipe.isEnabled = false
        lessonDoneProgress()
        val pointsGot = LessonConfig.POINTS_REWARD_DEFAULT
        user.points += pointsGot
        val completion = Completion(
            totalQuestion, totalQuestion, subpracticeID, user.ID
        )
        val activity = Activity(
            subpracticeID, user.ID, pointsGot
        )

        jowoanService.userUpdate(user.token, user.ID, user)
            .enqueue(APICallback(object : APICallback.Action<User> {
                override fun responseSuccess(data: User) {
                    toast("Berhasil mengupdate user")
                    saveUser(data)
                    user_update_done = true
                    handleResponsesDone()
                }

                override fun dataNotFound(message: String) {
                    toast("user data not found!")
                    user_update_done = true
                    handleResponsesDone()
                }

                override fun responseFailed(status: String, message: String) {
                    toast("User update gagal Status: $status Message: $message")
                    user_update_done = true
                    handleResponsesDone()
                }

                override fun networkFailed(t: Throwable) {
                    toast("User update gagal! ${t.message}")
                    user_update_done = true
                    handleResponsesDone()
                }

                override fun tokenExpired() {
                    toast("Token telah expired, silahkan login ulang")
                    logout()
                    Intent(this@LessonActivity, LoginActivity::class.java).also {
                        startActivity(it)
                        finishAffinity()
                    }
                }
            }))

        jowoanService.completionUpsert(user.token, completion)
            .enqueue(APICallback(object : APICallback.Action<Completion> {
                override fun responseSuccess(data: Completion) {
                    toast("Berhasil mengupdate completion")
                    completion_update_done = true
                    handleResponsesDone()
                }

                override fun dataNotFound(message: String) {
                    toast("completion data not found!")
                    completion_update_done = true
                    handleResponsesDone()
                }

                override fun responseFailed(status: String, message: String) {
                    toast("Completion update gagal Status: $status Message: $message")
                    completion_update_done = true
                    handleResponsesDone()
                }

                override fun networkFailed(t: Throwable) {
                    toast("Completion update gagal! ${t.message}")
                    completion_update_done = true
                    handleResponsesDone()
                }

                override fun tokenExpired() {
                    toast("Token telah expired, silahkan login ulang")
                    logout()
                    Intent(this@LessonActivity, LoginActivity::class.java).also {
                        startActivity(it)
                        finishAffinity()
                    }
                }

            }))

        jowoanService.activityCreate(user.token, activity)
            .enqueue(APICallback(object : APICallback.Action<Activity> {
                override fun responseSuccess(data: Activity) {
                    toast("Berhasil mengupdate activity")
                    activity_update_done = true
                    handleResponsesDone()
                }

                override fun dataNotFound(message: String) {
                    toast("activity data not found!")
                    activity_update_done = true
                    handleResponsesDone()
                }

                override fun responseFailed(status: String, message: String) {
                    toast("activity update gagal Status: $status Message: $message")
                    activity_update_done = true
                    handleResponsesDone()
                }

                override fun networkFailed(t: Throwable) {
                    toast("activity update gagal! ${t.message}")
                    activity_update_done = true
                    handleResponsesDone()
                }

                override fun tokenExpired() {
                    toast("Token telah expired, silahkan login ulang")
                    logout()
                    Intent(this@LessonActivity, LoginActivity::class.java).also {
                        startActivity(it)
                        finishAffinity()
                    }
                }

            }))
    }

    private fun lessonDoneProgress() {
        val progressText = progress.textView_description
        progressText.text = "Sedang menyimpan perkembangan kamu, mohon tunggu..."
        progress.visibility = View.VISIBLE
    }

    private fun handleResponsesDone() {
        if (user_update_done && completion_update_done && activity_update_done) {
            finish()
        }
    }
}