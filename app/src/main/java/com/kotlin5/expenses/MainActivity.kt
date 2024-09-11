package com.kotlin5.expenses

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin5.expenses.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val expenseViewModel: ExpenseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = ExpenseAdapter()

        binding.cashlist.adapter = adapter
        binding.cashlist.layoutManager = LinearLayoutManager(this) // Set the LayoutManager

        expenseViewModel.allExpenses.observe(this, Observer { expenses ->
            expenses?.let {
                adapter.submitList(it)
                logExpenses(it)  // Log the expenses
            }
        })

        binding.fab.setOnClickListener {
            val intent = Intent(this, AddExpenseActivity::class.java)
            startActivity(intent)
        }
    }

    private fun logExpenses(expenses: List<Expense>) {
        Log.d("MainActivity", "Expenses List: ${expenses.joinToString { "ID: ${it.id}, Name: ${it.name}, Amount: ${it.amount}, Date: ${it.date}, Type: ${it.type}" }}")
    }
}
