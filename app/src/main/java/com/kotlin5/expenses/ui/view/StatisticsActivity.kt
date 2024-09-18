package com.kotlin5.expenses.ui.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.kotlin5.expenses.R
import com.kotlin5.expenses.model.db.Expense
import com.kotlin5.expenses.ui.CustomValueFormatter
import com.kotlin5.expenses.ui.model.ExpenseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class StatisticsActivity : AppCompatActivity() {
    private lateinit var lineChart: LineChart
    private lateinit var chipGroup: ChipGroup
    private val expenseViewModel: ExpenseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)

        lineChart = findViewById(R.id.lineChart)
        chipGroup = findViewById(R.id.chipGroup2)

        // Set up chip selection listener
        chipGroup.setOnCheckedChangeListener { group, checkedId ->
            val chip = findViewById<Chip>(checkedId)
            when (chip?.id) {
                R.id.year -> filterExpenses("yearly")
                R.id.month -> filterExpenses("monthly")
                R.id.week -> filterExpenses("weekly")
                R.id.day -> filterExpenses("daily")
            }
        }

        // Observe the expenses from the ViewModel
        expenseViewModel.allExpenses.observe(this, Observer { expenses ->
            expenses?.let {
                fetchAndDisplayExpenses(it)
            }
        })

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }
                R.id.statistics -> true
                R.id.wallet -> {
                    startActivity(Intent(this, WalletActivity::class.java))
                    true
                }
                R.id.myprofile -> true
                else -> false
            }
        }

        if (savedInstanceState == null) {
            bottomNavigationView.selectedItemId = R.id.statistics
        }
    }

    private fun filterExpenses(timeFrame: String) {
        expenseViewModel.allExpenses.observe(this, { expenses ->
            val filteredExpenses = when (timeFrame) {
                "daily" -> filterByDays(expenses)
                "weekly" -> filterByWeeks(expenses)
                "monthly" -> filterByMonths(expenses)
                "yearly" -> filterByYears(expenses)
                else -> emptyList()
            }
            fetchAndDisplayExpenses(filteredExpenses)
        })
    }

    private fun filterByDays(expenses: List<Expense>): List<Expense> {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        return expenses.filter { it.date == today }
    }

    private fun filterByWeeks(expenses: List<Expense>): List<Expense> {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
        val startOfWeek = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.time)
        return expenses.filter { it.date >= startOfWeek }
    }

    private fun filterByMonths(expenses: List<Expense>): List<Expense> {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        val startOfMonth = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.time)
        return expenses.filter { it.date >= startOfMonth }
    }

    private fun filterByYears(expenses: List<Expense>): List<Expense> {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MONTH, 0)
        calendar.set(Calendar.DAY_OF_YEAR, 1)
        val startOfYear = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.time)
        return expenses.filter { it.date >= startOfYear }
    }

    private fun fetchAndDisplayExpenses(expenses: List<Expense>) {
        CoroutineScope(Dispatchers.IO).launch {
            // Log expenses for debugging
            logExpenses(expenses)

            // Prepare chart entries
            val entries = expenses.mapIndexed { index, expense ->
                Entry(index.toFloat(), expense.amount.toFloat())
            }

            // Create dataset and set data to the chart
            val dataSet = LineDataSet(entries, "Expenses")
            dataSet.color = resources.getColor(android.R.color.holo_blue_dark)
            dataSet.valueTextColor = resources.getColor(android.R.color.black)

            val lineData = LineData(dataSet)

            // Switch to the main thread to update UI
            withContext(Dispatchers.Main) {
                lineChart.data = lineData

                // Customize Y-axis (show amounts on the left)
                lineChart.axisLeft.apply {
                    setDrawLabels(true)
                    setDrawGridLines(true)
                    textColor = resources.getColor(android.R.color.black)
                    granularity = 10f // Adjust based on your amount scale
                    axisMinimum = 0f // Set minimum value
                }

                // Customize X-axis (show dates, month names, weekdays, and years)
                lineChart.xAxis.apply {
                    position = com.github.mikephil.charting.components.XAxis.XAxisPosition.BOTTOM
                    setDrawGridLines(false)
                    textColor = resources.getColor(android.R.color.black)
                    valueFormatter = CustomValueFormatter(expenses) // Custom formatter
                    granularity = 1f // Ensure each entry gets displayed
                    isGranularityEnabled = true
                }

                lineChart.axisRight.isEnabled = false // Disable right axis

                lineChart.invalidate() // Refresh the chart
            }
        }
    }

    private fun logExpenses(expenses: List<Expense>) {
        Log.d("StatisticsActivity", "Expenses List: ${expenses.joinToString { "ID: ${it.id}, Name: ${it.name}, Amount: ${it.amount}, Date: ${it.date}, Type: ${it.type}" }}")
    }
}
