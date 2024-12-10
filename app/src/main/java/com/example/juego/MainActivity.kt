package com.example.juego

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val playerNameInput = findViewById<EditText>(R.id.playerNameInput)
        val startGameButton = findViewById<Button>(R.id.startGameButton)

        startGameButton.setOnClickListener {
            val playerName = playerNameInput.text.toString()

            if (playerName.isEmpty()) {
                Toast.makeText(this, "Por favor ingrese un nombre", Toast.LENGTH_SHORT).show()
            } else if (playerName.length > 10 || playerName.first().isDigit()) {
                Toast.makeText(
                    this,
                    "El nombre no debe superar los 10 caracteres ni comenzar con un número",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val intent = Intent(this, GameActivity::class.java)
                intent.putExtra("PLAYER_NAME", playerName)
                startActivity(intent)
            }
        }
    }
}
