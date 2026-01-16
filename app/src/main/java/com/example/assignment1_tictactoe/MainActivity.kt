package com.example.assignment1_tictactoe

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val tvWinner = findViewById<TextView>(R.id.winnerText)
        val boardGrid = findViewById<android.widget.GridLayout>(R.id.boardGrid)
        val buttons = Array(9) { i ->
            boardGrid.getChildAt(i) as Button
        }

        val board = Array(3) {Array(3) {' '} }
        var currentPlayer = 'X'
        var movesCount = 0

        for (i in buttons.indices) {
            val btn = buttons[i]

            btn.setOnClickListener {
                if(btn.text.isNotEmpty()) return@setOnClickListener

                btn.text = currentPlayer.toString()

                val row = i/3
                val col = i%3
                board[row][col] = currentPlayer
                movesCount++

                if(checkWinner(board, currentPlayer)) {
                    tvWinner.text = "$currentPlayer wins!"
                    tvWinner.visibility = View.VISIBLE
                    disableBoard(buttons)
                    return@setOnClickListener
                }

                if(movesCount == 9) {
                    tvWinner.text = "Draw!"
                    tvWinner.visibility = View.VISIBLE
                    disableBoard(buttons)
                    return@setOnClickListener
                }

                currentPlayer = if(currentPlayer =='X') 'O' else 'X'
            }
        }

        val restartBtn = findViewById<Button>(R.id.restartBtn)
        restartBtn.setOnClickListener {
            resetGame(buttons, board)
            currentPlayer = 'X'
            movesCount = 0
            tvWinner.visibility = View.GONE

        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun checkWinner(board: Array<Array<Char>>, player: Char): Boolean {
        //Rows
        for(r in 0..2) {
            if(board[r][0]==player && board[r][1] == player && board[r][2]== player) return true
        }
        //Columns
        for (c in 0..2) {
            if (board[0][c] == player && board[1][c] == player && board[2][c] ==player) return true
        }
        //Diagonals
        if(board[0][0] == player && board[1][1] == player && board[2][2] == player) return true
        if(board[0][2] == player && board[1][1] == player && board[2][0] == player) return true

        return false
    }

    private fun disableBoard(buttons: Array<Button>) {
        for(b in buttons) {
            b.isEnabled = false
        }
    }

    private fun resetGame(buttons: Array<Button>, board: Array<Array<Char>>) {
        for(i in buttons.indices) {
            buttons[i].text = ""
            buttons[i].isEnabled = true
        }

        for(r in 0..2) {
            for (c in 0..2) {
                board[r][c] = ' '
            }
        }
    }
}