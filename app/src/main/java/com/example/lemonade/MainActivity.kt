package com.example.lemonade

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private val LEMONADE_STATE = "LEMONADE_STATE"
    private val LEMON_SIZE = "LEMON_SIZE"
    private val SQUEEZE_COUNT = "SQUEEZE_COUNT"

    private val SELECT = "select"
    private val SQUEEZE = "squeeze"
    private val DRINK = "drink"
    private val RESTART = "restart"

    private var lemonadeState = "select"
    private var lemonSize = -1
    private var squeezeCount = -1

    private var lemonTree = LemonTree()


    private var currentText = ""
    private var currentDrawable = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            lemonadeState = savedInstanceState.getString(LEMONADE_STATE, "select")
            lemonSize = savedInstanceState.getInt(LEMON_SIZE, -1)
            squeezeCount = savedInstanceState.getInt(SQUEEZE_COUNT, -1)
        }

        changeToSelectState()
        updateTextAction()
        updateLemonImage()

        lemonImage().setOnClickListener { clickLemonImage() }
        lemonImage().setOnLongClickListener { showSnackbar() }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(LEMONADE_STATE, lemonadeState)
        outState.putInt(LEMON_SIZE, lemonSize)
        outState.putInt(SQUEEZE_COUNT, squeezeCount)
        super.onSaveInstanceState(outState)
    }

    private fun changeToSelectState() {
        lemonadeState = SELECT

        currentText = "Click to select a lemon!"
        currentDrawable = R.drawable.lemon_tree
    }

    private fun changeToSqueezeState() {
        lemonadeState = SQUEEZE
        lemonSize = lemonTree.pick()
        squeezeCount = 0

        currentText = "Click to juice the lemon!"
        currentDrawable = R.drawable.lemon_squeeze
    }

    private fun changeToDrinkState() {
        squeezeCount++
        lemonSize--

        if (lemonSize == 0) {
            lemonadeState = DRINK

            currentText = "Click to drink your lemonade!"
            currentDrawable = R.drawable.lemon_drink

            return
        }
    }

    private fun changeToRestartState() {
        lemonadeState = RESTART

        currentText = "Click to start again!"
        currentDrawable = R.drawable.lemon_restart
    }

    private fun updateTextAction() {
        textAction().text = currentText
    }

    private fun updateLemonImage() {
        lemonImage().setImageResource(currentDrawable)
    }

    private fun clickLemonImage() {
        when (lemonadeState) {
            SELECT -> changeToSqueezeState()
            SQUEEZE -> changeToDrinkState()
            DRINK -> changeToRestartState()
            RESTART -> changeToSelectState()
        }

        updateTextAction()
        updateLemonImage()
    }

    private fun textAction(): TextView {
        return findViewById(R.id.text_action)!!
    }

    private fun lemonImage(): ImageView {
        return findViewById(R.id.image_lemon_state)!!
    }

    private fun showSnackbar(): Boolean {
        if (lemonadeState != SQUEEZE) {
            return false
        }

        val squeezeText = getString(R.string.squeeze_count, squeezeCount)
        Snackbar
            .make(
                findViewById(R.id.constraint_Layout),
                squeezeText,
                Snackbar.LENGTH_SHORT
            )
            .show()
        return true
    }
}

class LemonTree {
    fun pick(): Int {
        return (2..4).random()
    }
}
