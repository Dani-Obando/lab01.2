package com.example.juego

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.gridlayout.widget.GridLayout

class GameActivity : AppCompatActivity() {
    private var points = 0
    private var targetImage: Int = 0
    private val images = listOf(
        R.drawable.ic_img1, R.drawable.ic_img2, R.drawable.ic_img3,
        R.drawable.ic_img4, R.drawable.ic_img5, R.drawable.ic_img6
    )
    private lateinit var randomizedImages: MutableList<Int>
    private lateinit var gridLayout: GridLayout
    private lateinit var pointsText: TextView
    private lateinit var targetImageView: ImageView
    private var revealedImagesCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val playerName = intent.getStringExtra("PLAYER_NAME")
        val playerNameText = findViewById<TextView>(R.id.playerName)
        pointsText = findViewById(R.id.pointsText)
        gridLayout = findViewById(R.id.imageGrid)
        targetImageView = findViewById(R.id.targetImage)

        playerNameText.text = playerName ?: "Sin nombre"
        pointsText.text = points.toString()

        startGame()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_game, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_2_rounds -> startGame(2)
            R.id.action_3_rounds -> startGame(3)
            R.id.action_5_rounds -> startGame(5)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun startGame(rounds: Int = 1) {
        gridLayout.removeAllViews()
        revealedImagesCount = 0
        randomizedImages = images.shuffled().toMutableList()
        targetImage = randomizedImages.random()
        targetImageView.setImageResource(targetImage)

        for (i in images.indices) {
            val imageView = ImageView(this).apply {
                layoutParams = GridLayout.LayoutParams().apply {
                    width = 200
                    height = 200
                    marginStart = 8
                    marginEnd = 8
                    topMargin = 8
                    bottomMargin = 8
                }
                setImageResource(R.drawable.pattern_image)
                scaleType = ImageView.ScaleType.CENTER_CROP
            }

            imageView.setOnClickListener {
                if (imageView.drawable.constantState == resources.getDrawable(R.drawable.pattern_image).constantState) {
                    revealedImagesCount++
                    imageView.setImageResource(randomizedImages[i])
                    if (randomizedImages[i] == targetImage) {
                        points += 100
                        Toast.makeText(this, "¡Correcto! +100 puntos", Toast.LENGTH_SHORT).show()
                    } else {
                        points -= 10
                        Toast.makeText(this, "Incorrecto. -10 puntos", Toast.LENGTH_SHORT).show()
                    }
                    pointsText.text = points.toString()

                    if (revealedImagesCount == images.size) {
                        showCompletionMessage()
                    }
                }
            }

            gridLayout.addView(imageView)
        }
    }

    private fun showCompletionMessage() {
        AlertDialog.Builder(this)
            .setTitle("Juego Completo")
            .setMessage("Total de puntos: $points\n¿Quieres jugar otra ronda?")
            .setPositiveButton("Sí") { _, _ -> startGame() }
            .setNegativeButton("No") { _, _ -> finish() }
            .create()
            .show()
    }
}
