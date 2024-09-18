package com.kotlin5.expenses.ui

import com.github.mikephil.charting.formatter.ValueFormatter
import com.kotlin5.expenses.model.db.Expense
import java.text.SimpleDateFormat
import java.util.*

class CustomValueFormatter(private val expenses: List<Expense>) : ValueFormatter() {
    private val dateFormatter = SimpleDateFormat("dd MMM", Locale.getDefault())

    override fun getAxisLabel(value: Float, axis: com.github.mikephil.charting.components.AxisBase?): String {
        // Get the index and ensure it's within bounds
        val index = value.toInt()
        return if (index in expenses.indices) {
            val expenseDate = expenses[index].date
            // Parse the date string and format it
            try {
                val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(expenseDate)
                date?.let { dateFormatter.format(it) } ?: expenseDate
            } catch (e: Exception) {
                expenseDate // Fallback to the original date if parsing fails
            }
        } else {
            ""
        }
    }
}
