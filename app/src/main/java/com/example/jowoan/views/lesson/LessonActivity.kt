package com.example.jowoan.views.lesson

import android.content.Intent
import android.os.Bundle
import com.example.jowoan.R
import com.example.jowoan.adapters.LessonAdapter
import com.example.jowoan.config.LessonConfig
import com.example.jowoan.custom.AppCompatActivity
import com.example.jowoan.models.lesson.*
import com.example.jowoan.views.main.MainActivity
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.StackFrom
import kotlinx.android.synthetic.main.activity_lesson.*

class LessonActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lesson)

        cardStackView.layoutManager = CardStackLayoutManager(this).apply {
            setStackFrom(StackFrom.Top)
            setVisibleCount(3)
            setCanScrollHorizontal(false)
            setCanScrollVertical(false)
            setScaleInterval(0.90f)
            setTranslationInterval(12.0f)
        }
        cardStackView.adapter = LessonAdapter(
            mutableListOf(
                Lesson(
                    1, 1,
                    Penjelasan(1, "Poker Face", getString(R.string.lorem_ipsum)),
                    null, null, null, null, null, null, 1, 1
                ),
                Lesson(
                    1, 1, null, null,
                    Hafalan(
                        1,
                        "Hafalkan kata berikut ini",
                        "https://assets.kompasiana.com/items/album/2020/08/25/78a89f31-b98a-452c-b61b-3329cb1568b5-5f44ffdd097f361ba90b0dc3.jpeg",
                        "Mari pak!",
                        "Monggo pak!"
                    ),
                    null, null, null, null, 1, 3
                ),
                Lesson(
                    1, 1,
                    null, null, null,
                    PilihKata(
                        1,
                        5,
                        6,
                        "https://ep01.epimg.net/cultura/imagenes/2018/08/20/actualidad/1534776514_885749_1534778047_noticia_normal.jpg",
                        listOf(
                            PilihKataAnswer(1, "World", 1), PilihKataAnswer(2, "Cuties", 1)
                        ),
                        "Hello World!",
                        "Lengkapi kalimat di bawah ini",
                        "Tidak ada pembenaran"
                    ),
                    null, null, null, 1, 4
                ),
                Lesson(
                    1, 1, null,
                    Tips(1, "Unfaithful Wife", getString(R.string.lorem_ipsum)),
                    null, null, null, null, null, 1, 2
                ),
                Lesson(
                    1, 1, null, null, null, null,
                    Berbicara(
                        1,
                        "Dengar dan ucapkan pelafalan berikut ini",
                        "Mangga Pak",
                        "Mari Pak"
                    ),
                    null, null, 1, LessonConfig.BERBICARA_TYPE
                ),
                Lesson(
                    1, 1, null, null, null, null, null,
                    BenarSalah(
                        1,
                        "Ini title",
                        "Ini Statement",
                        "Kalo begitu mana questionnya dong?",
                        false,
                        "Tidak ada koreksi"
                    ),
                    null, 1, LessonConfig.BENAR_SALAH_TYPE
                ),
                Lesson(
                    1, 1, null, null, null, null, null, null,
                    LessonResult(
                        100,
                        "Kamu luar biasa!",
                        "Tetap jaga semangatmu dan selesaikan pelajaran berikutnya."
                    ),
                    1, LessonConfig.RESULT_TYPE
                )
            ), this
        )

        closed_lesson.setOnClickListener {
            Intent(applicationContext, MainActivity::class.java).also {
                startActivity(it)
                finishAffinity()
            }
        }

        var progress_number = 0
        swipe.setOnClickListener {
            progress_number += 5
            cardStackView.swipe()
            progress_soal.incrementProgressBy(progress_number)
        }
    }
}