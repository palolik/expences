package com.kotlin5.expenses

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpenseViewModel @Inject constructor(private val expenseRepository: ExpenseRepository) : ViewModel() {

    val allExpenses: LiveData<List<Expense>> = expenseRepository.getAllExpenses()

    fun insert(expense: Expense) = viewModelScope.launch {
        expenseRepository.insertExpense(expense)
    }

    fun update(expense: Expense) = viewModelScope.launch {
        expenseRepository.updateExpense(expense)
    }

    fun delete(expense: Expense) = viewModelScope.launch {
        expenseRepository.deleteExpense(expense)
    }

    fun getExpenseById(id: Int) = viewModelScope.launch {
        expenseRepository.getExpenseById(id)
    }
}
