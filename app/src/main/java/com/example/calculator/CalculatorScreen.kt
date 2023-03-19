package com.example.calculator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.calculator.databinding.FragmentCalculatorScreenBinding


class CalculatorScreen : Fragment() {

    private lateinit var binding : FragmentCalculatorScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalculatorScreenBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.btn0.setOnClickListener { addDigit('0') }
        binding.btnNumber1.setOnClickListener { addDigit('1') }
        binding.btnNumber2.setOnClickListener { addDigit('2') }
        binding.btnNumber3.setOnClickListener { addDigit('3') }
        binding.btnNumber4.setOnClickListener { addDigit('4') }
        binding.btnNumber5.setOnClickListener { addDigit('5') }
        binding.btnNumber6.setOnClickListener { addDigit('6') }
        binding.btnNumber7.setOnClickListener { addDigit('7') }
        binding.btnNumber8.setOnClickListener { addDigit('8') }
        binding.btnNumber9.setOnClickListener { addDigit('9') }

        binding.btnPlus.setOnClickListener { addDigit('+') }
        binding.btnPercent.setOnClickListener { addDigit('%') }
        binding.btnMinus.setOnClickListener { addDigit('-') }
        binding.btnDivision.setOnClickListener { addDigit('/') }
        binding.btnAC.setOnClickListener{ clearInputs() }
        binding.btnDelete.setOnClickListener{ deleteLastDigit() }

        return view
    }

    private fun addDigit(digit: Char) {
        val lastChar = binding.history.text.lastOrNull()
        val operators = arrayOf('+', '-', '%', '/')

        if(lastChar == null && digit in operators){
            binding.history.text = "0$digit"
        }
        else if(lastChar in operators && digit in operators){
            val newText = binding.history.text.toString().dropLast(1) + digit
            binding.history.text = newText
        }
        else{
            var currentText = binding.history.text.toString()
            binding.history.text = currentText + digit
        }

        val expression = binding.history.text.toString()
        val elements = expression.toCharArray()

        if(elements.size == 3 && expression.last().isDigit()){
            binding.resultInput.text = performOperation(elements[0].toString(), elements[2].toString(), elements[1].toString()).toString()
        }
        else if(elements.size > 3 && expression.last().isDigit()){
            val currentResult = binding.resultInput.text.toString()
            val lastTwoValues = elements.takeLast(2)

            binding.resultInput.text = performOperation(currentResult, lastTwoValues[1].toString(), lastTwoValues[0].toString()).toString()
        }
    }

    private fun performOperation(firstDigit: String, secondDigit: String, operator: String): Double {
        when (operator) {
            "+" -> {
                return (firstDigit.toDouble() + secondDigit.toDouble())
            }
            "-" -> {
                return (firstDigit.toDouble() - secondDigit.toDouble())
            }
            "/" -> {
                return (firstDigit.toDouble() / secondDigit.toDouble())
            }
            "%" -> {
                return (firstDigit.toDouble() % secondDigit.toDouble())
            }
            else -> {
                return 0.0
            }
        }
    }

    private fun clearInputs(){
        binding.history.text = ""
        binding.resultInput.text = ""
    }

    private fun deleteLastDigit(){
        val textAfterDelete = binding.history.text.toString().dropLast(1)
        binding.history.text = textAfterDelete
    }

}