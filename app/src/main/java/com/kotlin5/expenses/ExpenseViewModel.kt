package com.kotlin5.expenses
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpenseViewModel @Inject constructor(private val repository: ExpenseRepository) : ViewModel() {

    val allExpenses: LiveData<List<Expense>> = repository.getAllExpenses()

    fun insertExpense(expense: Expense) = viewModelScope.launch {
        repository.insertExpense(expense)
    }

    fun updateExpense(expense: Expense) = viewModelScope.launch {
        repository.updateExpense(expense)
    }

    fun deleteExpense(expense: Expense) = viewModelScope.launch {
        repository.deleteExpense(expense)
    }

//    fun getExpenseById(id: Int): LiveData<Expense?> {
//        // Use live data and a coroutine to fetch expense by ID
//    }
}
