package com.example.calculator.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.calculator.data.entities.CalculationHistory

@Dao
interface CalculationHistoryDao {
    @Query("SELECT * FROM calculation_history")
    fun getAllCalculations(): List<CalculationHistory>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCalculation(calculation: CalculationHistory)
}