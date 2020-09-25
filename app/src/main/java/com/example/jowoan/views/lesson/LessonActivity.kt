package com.example.jowoan.views.lesson

import android.content.Intent
import android.os.Bundle
import android.view.View
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

    val lessons = mutableListOf<Lesson>()
    private lateinit var adapter: LessonAdapter
    private var progressIncrement = 0

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
        adapter = LessonAdapter(lessons, this)
        cardStackView.adapter = adapter

        closed_lesson.setOnClickListener {
            Intent(applicationContext, MainActivity::class.java).also {
                startActivity(it)
                finishAffinity()
            }
        }

        swipe.setOnClickListener {
            cardStackView.swipe()
            progress_soal.incrementProgressBy(progressIncrement)
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
}