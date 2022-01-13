package com.example.tipcalculatorimproved

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.containsString
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CalculatorTests {
    @get:Rule()
    val activity = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun calculate_20_percent_tip() {
        fun writeValidValueInServiceCostInput() {
            onView(withId(R.id.cost_of_service_edit_text))
                .perform(typeText("50.00"), closeSoftKeyboard())
        }

        fun clickOnCalculateButton() {
            onView(withId(R.id.calculate_button))
                .perform(click())
        }

        fun checkIfValidTipString() {
            onView(withId(R.id.tip_result))
                .check(matches(withText(containsString("R$10,00"))))
        }

        writeValidValueInServiceCostInput()
        clickOnCalculateButton()
        checkIfValidTipString()
    }

    @Test
    fun calculate_18_percent_tip() {
        fun writeValidValueInServiceCostInput() {
            onView(withId(R.id.cost_of_service_edit_text))
                .perform(typeText("50.00"), closeSoftKeyboard())
        }
        
        fun clickOnEighteenPercentTipRadio() {
            onView(withId(R.id.option_eighteen_percent))
                .perform(click())
        }

        fun clickOnCalculateButton() {
            onView(withId(R.id.calculate_button))
                .perform(click())
        }

        fun checkIfValidTipString() {
            onView(withId(R.id.tip_result))
                .check(matches(withText(containsString("R$9,00"))))
        }

        writeValidValueInServiceCostInput()
        clickOnEighteenPercentTipRadio()
        clickOnCalculateButton()
        checkIfValidTipString()
    }

    @Test
    fun calculate_15_percent_tip() {
        fun writeValidValueInServiceCostInput() {
            onView(withId(R.id.cost_of_service_edit_text))
                .perform(typeText("50.00"), closeSoftKeyboard())
        }

        fun clickOnFifteenPercentTipRadio() {
            onView(withId(R.id.option_fifteen_percent))
                .perform(click())
        }

        fun clickOnCalculateButton() {
            onView(withId(R.id.calculate_button))
                .perform(click())
        }
        
        fun checkIfValidTipString() {
            onView(withId(R.id.tip_result))
                .check(matches(withText(containsString("R$8,00"))))
        }

        writeValidValueInServiceCostInput()
        clickOnFifteenPercentTipRadio()
        clickOnCalculateButton()
        checkIfValidTipString()
    }
    
    @Test
    fun calculate_15_percent_tip_without_rounding_up() {
        fun writeValidValueInServiceCostInput() {
            onView(withId(R.id.cost_of_service_edit_text))
                .perform(typeText("50.00"), closeSoftKeyboard())
        }
        
        fun clickOnFifteenPercentTipRadio() {
            onView(withId(R.id.option_fifteen_percent))
                .perform(click())
        }

        fun disableRoundUpButton() {
            onView(withId(R.id.round_up_switch))
                .perform(click())
        }
        
        fun clickOnCalculateButton() {
            onView(withId(R.id.calculate_button))
                .perform(click())
        }

        fun checkIfValidTipString() {
            onView(withId(R.id.tip_result))
                .check(matches(withText(containsString("R$7,50"))))
        }

        writeValidValueInServiceCostInput()
        clickOnFifteenPercentTipRadio()
        disableRoundUpButton()
        clickOnCalculateButton()
        checkIfValidTipString()
    }
}