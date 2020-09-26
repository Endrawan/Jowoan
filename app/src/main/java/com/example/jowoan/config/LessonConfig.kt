package com.example.jowoan.config

import com.example.jowoan.models.lesson.*

object LessonConfig {
    val RESULT_TYPE = 9999
    val PENJELASAN_TYPE = 1
    val TIPS_TYPE = 2
    val HAFALAN_TYPE = 3
    val PILIH_KATA_TYPE = 4
    val BERBICARA_TYPE = 5
    val BENAR_SALAH_TYPE = 6

    val ANSWER_CORRECT = 1000
    val ANSWER_WRONG = 2000
    val ANSWER_HASNT_ANSWERED = 3000

    val Dummy = mutableListOf(
        Lesson(
            1, 1,
            Penjelasan(1, "Poker Face", "This is lorem ipsum"),
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
            Tips(1, "Unfaithful Wife", "This is lorem ipsum"),
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
    )

    val resultTemplate = Lesson(
        1, 1, null, null, null, null, null, null,
        LessonResult(
            100,
            "Kamu luar biasa!",
            "Tetap jaga semangatmu dan selesaikan pelajaran berikutnya."
        ),
        1, LessonConfig.RESULT_TYPE
    )
}