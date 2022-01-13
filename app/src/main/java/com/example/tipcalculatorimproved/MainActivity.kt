package com.example.tipcalculatorimproved

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.tipcalculatorimproved.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        updateTipText()
        binding.calculateButton.setOnClickListener {
            updateTipText()
        }
        binding.costOfServiceEditText.setOnKeyListener {
                view, keyCode, _ -> handleKeyEvent(view, keyCode)
        }
    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean{
        return when (keyCode) {
            KeyEvent.KEYCODE_ENTER ->  {
                hideKeyboard(view)
                true
            }
            else -> {
                false
            }
        }
    }

    private fun updateTipText() {
        binding.tipResult.text = getString(R.string.tip_amount, formatTipAsCurrency(calculateTip()))
    }

    private fun calculateTip(): Double {
        var tip = getCost() * getTipPercentage()

        if (roundUpTip()) {
            tip = kotlin.math.ceil(tip)
        }

        return tip
    }

    private fun getCost(): Double {
        val stringInTextField = binding.costOfServiceEditText.text.toString()
        var cost = stringInTextField.toDoubleOrNull()

        if (cost == null) cost = 0.0

        return cost
    }

    private fun getTipPercentage(): Double {
        val selectedId = binding.tipOptions.checkedRadioButtonId
        val tipPercentage = when (selectedId) {
            R.id.option_twenty_percent -> 0.2
            R.id.option_eighteen_percent -> 0.18
            R.id.option_fifteen_percent -> 0.15
            else -> 0.0
        }
        return tipPercentage
    }

    private fun roundUpTip(): Boolean {
        return binding.roundUpSwitch.isChecked
    }

    private fun formatTipAsCurrency(tip: Double): String {
        return NumberFormat.getCurrencyInstance().format(tip)
    }
}