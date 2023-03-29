package com.example.calculator.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "calculation_history")
data class CalculationHistory(
    @PrimaryKey(autoGenerate = true) val id: Int? = 0,
    @ColumnInfo(name = "expression") val expression: String,
    @ColumnInfo(name = "result") val result: Double,
    @ColumnInfo(name = "timestamp") val timestamp: Long
)