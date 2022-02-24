package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var canAddOperation = false
    private var canAddDecimal = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun allClearAction(view: View) {
        workingTV.text = ""
        resultTV.text = ""
        Toast.makeText(this,"Please type again", Toast.LENGTH_SHORT).show()
    }


    fun numberAction(view: View) {

        if (view is Button) {
            if (view.text == ".") {
                if (canAddOperation)
                    workingTV.append(view.text)
                canAddDecimal = false
            } else
                workingTV.append(view.text)

            canAddOperation = true
        }

        Toast.makeText(this,workingTV.text.last().toString(), Toast.LENGTH_SHORT).show()
    }

    fun operationAction(view: View) {
        if (view is Button && canAddOperation) {
            workingTV.append(view.text)
            canAddOperation = false
            canAddDecimal = true
        }
        Toast.makeText(this,workingTV.text.last().toString(), Toast.LENGTH_SHORT).show()
    }

    fun equalsAction(view: View) {
        resultTV.text = calculateResult()
        Toast.makeText(this,"equalsAction", Toast.LENGTH_SHORT).show()
    }

    private fun calculateResult(): String {

        val digitsOperators = digitsOperation()
        if (digitsOperators.isEmpty())  return ""

        val timesDivision = timeDivisionCalculate(digitsOperators)
        if (timesDivision.isEmpty())  return ""

        val result = addSubtractCalculate(timesDivision)

        return result.toString()
    }

    private fun addSubtractCalculate(passedList: MutableList<Any>): Float { // mohasebe jam va tafrigh
        var result = passedList[0] as Float

        for (i in passedList.indices){
            if (passedList[i] is Char && i != passedList.lastIndex){
                val operator = passedList[i]
                val nextDigit = passedList[i+1] as Float
                if(operator =='+')
                    result +=nextDigit
                if(operator =='-')
                    result -=nextDigit
            }
        }
        return result
    }

    private fun timeDivisionCalculate(passedList: MutableList<Any>): MutableList<Any> {  // mohasebe zard o taghsim
        var list = passedList
        while( list.contains('*')  || list.contains('/')){
            list = callTimesDiv(list)
        }
        return  list
    }

    private fun callTimesDiv(passedList: MutableList<Any>): MutableList<Any> {
            val newlist = mutableListOf<Any>()
            var restartIndex = passedList.size

            for (i in passedList.indices){
                if( passedList[i] is Char && i != passedList.lastIndex && i < restartIndex){
                   val operator = passedList[i]
                   val prevDigit = passedList[i-1] as Float
                   val nextDigit = passedList[i+1] as Float

                    when(operator){
                        '*' ->
                        {
                            newlist.add(prevDigit*nextDigit)
                            restartIndex = i+1
                        }

                        '/' ->
                        {
                            newlist.add(prevDigit/nextDigit)
                            restartIndex = i+1
                        }
                        else ->
                        {
                            newlist.add(prevDigit)
                            newlist.add(operator)
                        }
                    }
                }
                if (i > restartIndex )
                    newlist.add(passedList[i])
            }
            return newlist
    }

    private fun digitsOperation(): MutableList<Any> {  // reshtei az adad gerefte va dar yek list zakhire mikonad
        val list = mutableListOf<Any>()
        var currentDigit = ""
        for (charecter in workingTV.text) {
            if (charecter.isDigit() || charecter == '.')
                currentDigit += charecter
            else {
                list.add(currentDigit.toFloat())
                currentDigit = ""
                list.add(charecter)
            }
        }
        if (currentDigit != "")
            list.add(currentDigit.toFloat())

            return list
    }
}