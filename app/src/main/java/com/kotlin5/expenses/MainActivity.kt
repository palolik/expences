package com.kotlin5.expenses

import AddExpenseActivity
import android.content.Intent
import android.os.Bundle
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

        // Set up RecyclerView
        val adapter = ExpenseAdapter()
        binding.cashlist.layoutManager = LinearLayoutManager(this)
        binding.cashlist.adapter = adapter

        // Observe changes in the expense list
        expenseViewModel.allExpenses.observe(this, Observer { expenses ->
            expenses?.let { adapter.submitList(it) }
        })

        // Set up FAB to add expenses
        binding.fab.setOnClickListener {
            val intent = Intent(this, AddExpenseActivity::class.java)
            startActivity(intent)
        }
    }
}
