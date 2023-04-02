package com.example.calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.example.calculator.data.database.AppDatabase
import com.example.calculator.data.entities.CalculationHistory
import com.example.calculator.databinding.FragmentCalculatorScreenBinding


class CalculatorScreen : Fragment() {

    private lateinit var binding : FragmentCalculatorScreenBinding
    var db: AppDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalculatorScreenBinding.inflate(inflater, container, false)
        val view = binding.root

        this.db = Room.databaseBuilder(
            requireContext().applicationContext,
            AppDatabase::class.java, "item_database"
        ).allowMainThreadQueries().build()

        binding.btn0.setOnClickListener { updateNewDigit('0') }
        binding.btnNumber1.setOnClickListener { updateNewDigit('1') }
        binding.btnNumber2.setOnClickListener { updateNewDigit('2') }
        binding.btnNumber3.setOnClickListener { updateNewDigit('3') }
        binding.btnNumber4.setOnClickListener { updateNewDigit('4') }
        binding.btnNumber5.setOnClickListener { updateNewDigit('5') }
        binding.btnNumber6.setOnClickListener { updateNewDigit('6') }
        binding.btnNumber7.setOnClickListener { updateNewDigit('7') }
        binding.btnNumber8.setOnClickListener { updateNewDigit('8') }
        binding.btnNumber9.setOnClickListener { updateNewDigit('9') }

        binding.btnPlus.setOnClickListener { updateNewDigit('+') }
        binding.btnMinus.setOnClickListener { updateNewDigit('-') }
        binding.btnDivision.setOnClickListener { updateNewDigit('/') }
        binding.btnVirgula.setOnClickListener { updateNewDigit(',') }
        binding.btnMultiplication.setOnClickListener { updateNewDigit('*') }

        binding.btnAC.setOnClickListener{ clearInputs() }
        binding.btnDelete.setOnClickListener{ deleteLastDigit() }

        binding.btnSave.setOnClickListener {
            val calculationHistoryDao = this.db!!.calculationHistoryDao();
            val calculationHistory = CalculationHistory(null, binding.history.text.toString(), binding.resultInput.text.toString().toDouble(), System.currentTimeMillis())

            calculationHistoryDao.insertCalculation(calculationHistory);
        }

        return view
    }

    private fun updateNewDigit(digit: Char) {
        fillExpression(digit)
        showResult()
    }

    private fun fillExpression(digit: Char){
        val lastChar = binding.history.text.lastOrNull()
        val operators = arrayOf('+', '-', '*', '/')

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
    }

    private fun showResult(){
        val expression = binding.history.text.toString()
        val elements = expression.split("(?=[+\\-*/])|(?<=[+\\-*/])".toRegex()).toTypedArray()

//        Regex explanation:
//        (?=[+\\-*/]) - Positive lookahead to identify if the subsequent character is one of the operators (+, -, *, or /).
//        | - Ou.
//        (?<=[+\\-*/]) - Lookahead positive to identify if the subsequent character is one of the operators (+, -, * or /).

        if(elements.size == 3 && expression.last().isDigit()){
            binding.resultInput.text = performOperation(elements[0].toString(), elements[2].toString(), elements[1].toString()).toString()
        }
        else if(elements.size > 3 && expression.last().isDigit()){
            val currentResult = binding.resultInput.text.toString()
            val lastTwoValues = elements.takeLast(2)

            binding.resultInput.text = performOperation(currentResult, lastTwoValues[1].toString(), lastTwoValues[0].toString()).toString()
        }
        else if(elements.size == 1 && expression.last().isDigit()){
            binding.resultInput.text = elements[0].toString()
        }
    }

    private fun performOperation(firstDigit: String, secondDigit: String, operator: String): Double {
        val formattedFirstDigit = if(firstDigit.contains(',')) firstDigit.replace(',', '.') else firstDigit
        val formattedSecondDigit = if(secondDigit.contains(',')) secondDigit.replace(',', '.') else secondDigit

        return when (operator) {
            "+" -> {
                (formattedFirstDigit.toDouble() + formattedSecondDigit.toDouble())
            }
            "-" -> {
                (formattedFirstDigit.toDouble() - formattedSecondDigit.toDouble())
            }
            "/" -> {
                (formattedFirstDigit.toDouble() / formattedSecondDigit.toDouble())
            }
            "*" -> {
                (formattedFirstDigit.toDouble() * formattedSecondDigit.toDouble())
            }
            else -> {
                0.0
            }
        }
    }

    private fun clearInputs(){
        binding.history.text = ""
        binding.resultInput.text = ""
    }

    private fun deleteLastDigit(){ // TODO: reverse result
        val textAfterDelete = binding.history.text.toString().dropLast(1)
        binding.history.text = textAfterDelete
        if(binding.history.text.toString() != "" && binding.history.text.toString() != null){
            showResult()
        }
        else{
            binding.resultInput.text = "0"
        }
    }
}