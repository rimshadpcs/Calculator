package com.rimapps.calculator

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    // TextView used to display the input and output
    private lateinit var txtInput: TextView

    // to check whether the last input is numeric or not
    private var lastNumeric: Boolean = false

    // to check that current state is in error or not
    private var stateError: Boolean = false

    // If stateError is true, shouldnt't append another decimal
    private var lastDecimal: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.txtInput = findViewById(R.id.txtInput)
    }

    /**
     * Append the Button.text to the TextView
     */
    fun onDigit(view: View) {
        if (this.stateError) {
            // If current state is Error, replace the error message
            txtInput.text = (view as Button).text
            stateError = false
        } else {
            // If not, valid expression is already there so append to it
            txtInput.append((view as Button).text)
        }
        // Set the flag
        lastNumeric = true
    }
    /**
     * Append . to the TextView
     */
    fun onDecimalPoint(view: View) {
        if (lastNumeric && !stateError && !lastDecimal) {
            txtInput.append(".")
            lastNumeric = false
            lastDecimal = true
        }
    }
    /**
     * Append +,-,*,/ operators to the TextView
     */
    fun onOperator(view: View) {
        if (lastNumeric && !stateError) {
            txtInput.append((view as Button).text)
            lastNumeric = false
            lastDecimal = false
        }
    }
    /**
     * Clear the TextView
     */
    fun onClear(view: View) {
        this.txtInput.text = ""
        lastNumeric = false
        stateError = false
        lastDecimal = false
    }

    /**
     * Calculate the output using Exp4j library
     */
    @SuppressLint("SetTextI18n")
    fun onEqual(view: View) {
        // If the current state is error, nothing to do
        // If the last input is numerical, calculate
        if (lastNumeric && !stateError) {
            // Read the expression
            val txt = txtInput.text.toString()
            // Create an Expression (A class from exp4j library)
            val expression = ExpressionBuilder(txt).build()
            try {
                // Calculate the result and set to textview
                val result = expression.evaluate()
                txtInput.text = result.toString()
                lastDecimal = true // Result contains a dot
            } catch (ex: ArithmeticException) {
                // Display an error message
                txtInput.text = "Error"
                stateError = true
                lastNumeric = false
            }
        }
    }
    fun onMeanMedian(view: View) {
        val intent = Intent(this@MainActivity, OptionActivity::class.java)
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
    }

    /**
     * Open activity to calculate mean and median
     */
    fun onDelete(view: View) {
        val inputVal = txtInput.text.toString()
        if (inputVal.length > 0) {
            txtInput.setText(inputVal.substring(0, inputVal.length - 1))
        }
 }
}