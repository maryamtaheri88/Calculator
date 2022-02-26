package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.calculator.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    lateinit var binding: ActivityMainBinding

    private var canAddOperation = false
    private var canAddDecimal = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
        return ""
    }
}