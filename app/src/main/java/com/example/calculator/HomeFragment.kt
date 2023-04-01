package com.example.calculator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.example.calculator.data.database.AppDatabase
import com.example.calculator.data.entities.CalculationHistory
import com.example.calculator.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    var db: AppDatabase? = null
    private lateinit var adapter: HistoryListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        this.db = Room.databaseBuilder(
            requireContext().applicationContext,
            AppDatabase::class.java, "item_database"
        ).allowMainThreadQueries().build()

        val navController = findNavController()
        binding.btnHome.setOnClickListener { navController.navigate(R.id.action_homeFragment_to_calculatorScreen) }

        val calculationHistoryDao = this.db!!.calculationHistoryDao();
        val history = calculationHistoryDao.getAllCalculations()
        val calculationHistoryList = binding.calculatorList as ListView
        adapter = CustomHistoryListAdapter(requireContext(), R.layout.list_item_history, history)

        calculationHistoryList.adapter = adapter;

        return binding.root
    }
}