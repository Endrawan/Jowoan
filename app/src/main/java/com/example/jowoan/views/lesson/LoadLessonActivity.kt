package com.example.jowoan.views.lesson

import android.content.Intent
import android.os.Bundle
import com.example.jowoan.R.layout.activity_load_lesson
import com.example.jowoan.custom.AppCompatActivity
import com.example.jowoan.models.lesson.Lesson
import com.example.jowoan.network.APICallback
import com.google.gson.Gson

class LoadLessonActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_load_lesson)

        loadLesson()
    }

    private fun loadLesson() {
        val subpracticeID = intent.getIntExtra("SubpracticeID", 0)
        jowoanService.lessonGet(user.token, subpracticeID.toString())
            .enqueue(APICallback(object : APICallback.Action<List<Lesson>> {
                override fun responseSuccess(data: List<Lesson>) {
                    toast("Response Success")
                    val gson = Gson()
                    val intent = Intent(this@LoadLessonActivity, LessonActivity::class.java)
                    intent.putExtra("LESSON", gson.toJson(gson))
                    startActivity(intent)
                    finish()
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
                }
            }))
    }
}