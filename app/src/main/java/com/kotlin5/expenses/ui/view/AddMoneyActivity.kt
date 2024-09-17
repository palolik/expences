package com.kotlin5.expenses.ui.view

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kotlin5.expenses.R
import com.kotlin5.expenses.databinding.ActivityAddMoneyBinding
import com.kotlin5.expenses.model.db.Expense
import com.kotlin5.expenses.ui.model.ExpenseViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class AddMoneyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddMoneyBinding
    private val expenseViewModel: ExpenseViewModel by viewModels()
    private var expenseType: String = "Cash In" // Default to "Cash In"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMoneyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnDatePicker.setOnClickListener {
            showDatePicker()
        }
        binding.btnAdd.setOnClickListener {
            val name = binding.etName.text.toString()
            val amount = binding.etAmount.text.toString().toDoubleOrNull() ?: 0.0
            val date = binding.tvDate.text.toString()
            if (name.isBlank()) {
                Toast.makeText(this, "Please enter a name for the expense", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (amount == 0.0) {
                Toast.makeText(this, "Please enter a valid amount", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val newExpense = Expense(
                name = name,
                amount = amount,
                date = date,
                type = expenseType
            )
            expenseViewModel.insert(newExpense)
            finish()
        }
        setDefaultDate()
    }



    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = Calendar.getInstance().apply {
                    set(selectedYear, selectedMonth, selectedDay)
                }.time
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                binding.tvDate.text = dateFormat.format(selectedDate)
            },
            year, month, day
        )
        datePickerDialog.show()
    }

    private fun setDefaultDate() {
        val currentDate = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        binding.tvDate.text = dateFormat.format(currentDate)
    }
}
