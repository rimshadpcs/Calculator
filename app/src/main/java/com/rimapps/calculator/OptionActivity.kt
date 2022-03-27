package com.rimapps.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class OptionActivity : AppCompatActivity() {
    // TextView used to display the input and output
    private lateinit var txtInputOpt: TextView

    // check whether the last character key is numeric or not
    private var lastNumericOpt: Boolean = false

    // to check current state is error or not
    private var stateErrorOpt: Boolean = false

    //if true , do not allow to add another COMMA
    private var lastCommaOpt: Boolean = false

    // If true, do not allow to add another decimal point
    private var lastDecimalOpt: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_option)
        txtInputOpt = findViewById(R.id.txtInputOpt)
    }

    /**
     * Append the Button.text to the TextView
     */
    fun onDigitOpt(view: View) {
        // If not, already there is a valid expression so append to it
        if (this.stateErrorOpt) {
            // If current state is Error, replace the error message
            txtInputOpt.text = (view as Button).text
            stateErrorOpt = false
        } else {
            txtInputOpt.append((view as Button).text)
        }
        // If current state is Error, replace the error message
        // Set the flag
        lastNumericOpt = true
    }

    /**
     * Append . to the TextView
     */
    fun onDecimalPointOpt(view: View) {
        if (lastNumericOpt && !stateErrorOpt && !lastDecimalOpt) {
            txtInputOpt.append(".")
            lastNumericOpt = false
            lastDecimalOpt = true
            lastCommaOpt = true
        }
    }
    /**
     * Append "," operator to the TextView
     */
    fun onCommaOpt(view: View) {
        if (lastNumericOpt && !stateErrorOpt) {
            txtInputOpt.append(",")
            lastNumericOpt = false
            lastDecimalOpt = false

        }
    }
    /**
     * Finding mean value
     */
    fun onMeanOpt(view: View) {
        var inputVal: String = txtInputOpt.text.toString()
        // to seperate values before adding to the array
        if (inputVal.contains(",")) {
            // if the last value is a comma, it should not need to be in the array
            if(inputVal.last() == ',') inputVal = inputVal.dropLast(1)

            val meanArray: Array<Float> =
                inputVal.split(",").map { it.toFloat() }.toTypedArray()
            // average function allow to calculate mean: no need to get sum of array and divide by length of the array
            val meanValue: Double = meanArray.average()
            txtInputOpt.text = meanValue.toString()

        }
        if(!inputVal.contains(",")) {
            val toast =
                Toast.makeText(applicationContext, R.string.seperarte, Toast.LENGTH_SHORT)
            toast.show()
        }

    }

    fun onMedianOpt(view: View) {
        var inputVal: String = txtInputOpt.text.toString()
        // to seperate values before adding to the array
        if (inputVal.contains(",")) {
            // if the last value is a comma, it should not need to be in the array
            if(inputVal.last() == ',')  inputVal = inputVal.dropLast(1)
            val temp = inputVal.split(",")
            val medianArray: Array<Float> =
                temp.map { if (it != "") it.toFloat() else return }.toTypedArray()
            medianArray.sort()
            //case when array length is odd: middle element is the median in a sorted array
            var medianValue: Float =  (medianArray[medianArray.size / 2])

            //case when array length is even: sum of two middle elements divided by 2 is the median in that case
            if (medianArray.size % 2 == 0)  medianValue = (((medianArray[medianArray.size / 2] + medianArray[medianArray.size / 2 - 1]) / 2))
            txtInputOpt.text = medianValue.toString()
        }
        else{
            val toast = Toast.makeText(applicationContext, R.string.seperarte,Toast.LENGTH_SHORT)
            toast.show()
        }
    }

    /**
     * Clear the TextView
     */
    fun onClearOpt(view: View) {
        this.txtInputOpt.text = ""
        lastNumericOpt = false
        stateErrorOpt = false
        lastDecimalOpt = false
        lastCommaOpt = false
    }

    fun onBackSpaceOpt(view: View) {
    val inputVal = txtInputOpt.text.toString();

        if(inputVal.length>0){
            //remove characters from the end
            txtInputOpt.setText(inputVal.substring(0,inputVal.length-1))
        }
    }
}
