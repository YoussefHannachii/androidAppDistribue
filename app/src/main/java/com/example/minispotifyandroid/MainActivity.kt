package com.example.minispotifyandroid

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.annotation.Nullable
import androidx.core.view.isInvisible
import java.util.Locale




class MainActivity : ComponentActivity() {
private val REQUEST_CODE_SPEECH_INPUT = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val restartButton = findViewById<Button>(R.id.restartCommandButton)
        val audioCommandText: TextView = findViewById(R.id.audioCommandText)
        restartButton.setOnClickListener {
            audioCommandText.isInvisible = true
            restartButton.isInvisible = true
            startVoiceInput()
        }
        startVoiceInput()
    }

    private fun startVoiceInput() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Parlez maintenant...")

        startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                val spokenText = result?.get(0)
                // Le texte enregistré est dans la variable spokenText
                val audioCommandText: TextView = findViewById(R.id.audioCommandText)
                audioCommandText.isInvisible = false
                audioCommandText.text = "Texte enregistré : $spokenText"
                Toast.makeText(this, "Vous avez dit : $spokenText", Toast.LENGTH_SHORT).show()
                val restartButton = findViewById<Button>(R.id.restartCommandButton)
                restartButton.isInvisible = false

                // Vous pouvez maintenant utiliser spokenText comme vous le souhaitez
            } else {
                // Gérer les cas où la reconnaissance vocale n'a pas réussi
                Toast.makeText(this, "Échec de la reconnaissance vocale", Toast.LENGTH_SHORT).show()
            }
        }
    }
}