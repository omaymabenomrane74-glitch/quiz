package com.example.quiz

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

data class Question(
    val texte: String,
    val image: Int,
    val choix: Array<String>,
    val bonneReponse: Int,
    val description: String
)

class MainActivity : AppCompatActivity() {

    // Éléments de l'interface
    private lateinit var txtScore: TextView
    private lateinit var txtNumero: TextView
    private lateinit var imgQuestion: ImageView
    private lateinit var txtQuestion: TextView
    private lateinit var txtMessage: TextView
    private lateinit var txtResultat: TextView

    private lateinit var progressQuiz: ProgressBar

    private lateinit var btnChoix1: Button
    private lateinit var btnChoix2: Button
    private lateinit var btnChoix3: Button
    private lateinit var btnSuivant: Button
    private lateinit var btnRejouer: Button

    // Variables du quiz
    private var numeroQuestion = 0
    private var score = 0
    private var questionRepondue = false

    // Les 5 questions
    private val questions = arrayOf(

        Question(
            "Quelle est cette forme ?",
            R.drawable.triangle,
            arrayOf("Triangle", "Cercle", "Carré"),
            0,
            "Image d'un triangle"
        ),

        Question(
            "Quelle est cette forme ?",
            R.drawable.carre,
            arrayOf("Rectangle", "Carré", "Étoile"),
            1,
            "Image d'un carré"
        ),

        Question(
            "Quelle est cette forme ?",
            R.drawable.cercle,
            arrayOf("Circle", "Triangle", "Carré"),
            0,
            "Image d'un cercle"
        ),

        Question(
            "Quelle est cette forme ?",
            R.drawable.etoile,
            arrayOf("Rectangle", "Cercle", "Étoile"),
            2,
            "Image d'une étoile"
        ),

        Question(
            "Quelle est cette forme ?",
            R.drawable.rectangle,
            arrayOf("Cercle", "Rectangle", "Triangle"),
            1,
            "Image d'un rectangle"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        // Relier Kotlin aux éléments XML
        txtScore = findViewById(R.id.txt_score)
        txtNumero = findViewById(R.id.txt_numero)
        imgQuestion = findViewById(R.id.img_question)
        txtQuestion = findViewById(R.id.txt_question)
        txtMessage = findViewById(R.id.txt_message)
        txtResultat = findViewById(R.id.txt_resultat)

        progressQuiz = findViewById(R.id.progress_quiz)

        btnChoix1 = findViewById(R.id.btn_choix1)
        btnChoix2 = findViewById(R.id.btn_choix2)
        btnChoix3 = findViewById(R.id.btn_choix3)
        btnSuivant = findViewById(R.id.btn_suivant)
        btnRejouer = findViewById(R.id.btn_rejouer)

        // Afficher la première question
        afficherQuestion()

        // Gestion des réponses
        btnChoix1.setOnClickListener {
            verifierReponse(0)
        }

        btnChoix2.setOnClickListener {
            verifierReponse(1)
        }

        btnChoix3.setOnClickListener {
            verifierReponse(2)
        }

        // Question suivante
        btnSuivant.setOnClickListener {
            questionSuivante()
        }

        // Rejouer
        btnRejouer.setOnClickListener {
            rejouer()
        }
    }

    private fun afficherQuestion() {

        val question = questions[numeroQuestion]

        txtNumero.text = "Question ${numeroQuestion + 1} sur 5"
        txtQuestion.text = question.texte
        imgQuestion.setImageResource(question.image)
        imgQuestion.contentDescription = question.description

        btnChoix1.text = question.choix[0]
        btnChoix2.text = question.choix[1]
        btnChoix3.text = question.choix[2]

        txtScore.text = "Score : $score/5"

        progressQuiz.progress = numeroQuestion

        txtMessage.text = ""

        btnChoix1.isEnabled = true
        btnChoix2.isEnabled = true
        btnChoix3.isEnabled = true

        questionRepondue = false

        btnSuivant.visibility = View.GONE
    }

    private fun verifierReponse(reponse: Int) {

        // Empêcher de répondre plusieurs fois
        if (questionRepondue) {
            return
        }

        questionRepondue = true

        val question = questions[numeroQuestion]

        if (reponse == question.bonneReponse) {

            score++

            txtMessage.text = "Bonne réponse !"
            txtScore.text = "Score : $score/5"

        } else {

            txtMessage.text = "Mauvaise réponse !"
        }

        // La progression avance dans tous les cas
        progressQuiz.progress = numeroQuestion + 1

        // Désactiver les 3 boutons
        btnChoix1.isEnabled = false
        btnChoix2.isEnabled = false
        btnChoix3.isEnabled = false

        // Afficher le bouton suivant
        btnSuivant.visibility = View.VISIBLE

        if (numeroQuestion == questions.size - 1) {
            btnSuivant.text = "Voir le résultat"
        } else {
            btnSuivant.text = "Question suivante"
        }
    }

    private fun questionSuivante() {

        if (!questionRepondue) {
            return
        }

        if (numeroQuestion < questions.size - 1) {

            numeroQuestion++

            afficherQuestion()

        } else {

            afficherResultat()
        }
    }

    private fun afficherResultat() {

        txtNumero.text = "Quiz terminé !"

        txtQuestion.visibility = View.GONE
        imgQuestion.visibility = View.GONE

        btnChoix1.visibility = View.GONE
        btnChoix2.visibility = View.GONE
        btnChoix3.visibility = View.GONE
        btnSuivant.visibility = View.GONE

        txtMessage.visibility = View.GONE

        txtResultat.text = "Votre score : $score/5"
        txtResultat.visibility = View.VISIBLE

        btnRejouer.visibility = View.VISIBLE
    }

    private fun rejouer() {

        numeroQuestion = 0
        score = 0
        questionRepondue = false

        txtQuestion.visibility = View.VISIBLE
        imgQuestion.visibility = View.VISIBLE

        btnChoix1.visibility = View.VISIBLE
        btnChoix2.visibility = View.VISIBLE
        btnChoix3.visibility = View.VISIBLE

        txtMessage.visibility = View.VISIBLE

        txtResultat.visibility = View.GONE
        btnRejouer.visibility = View.GONE

        progressQuiz.progress = 0

        afficherQuestion()
    }
}