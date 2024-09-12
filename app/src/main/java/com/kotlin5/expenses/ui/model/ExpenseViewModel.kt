package com.kotlin5.expenses.ui.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin5.expenses.model.db.Expense
import com.kotlin5.expenses.model.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpenseViewModel @Inject constructor(private val expenseRepository: ExpenseRepository) : ViewModel() {

     val allExpenses: LiveData<List<Expense>> = expenseRepository.getAllExpenses()
    val totalBalance: MediatorLiveData<Double> = MediatorLiveData()
    val totalIncome: MediatorLiveData<Double> = MediatorLiveData()
    val totalExpenses: MediatorLiveData<Double> = MediatorLiveData()

    init {
        totalBalance.addSource(allExpenses) { expenses ->
            val income = expenses.filter { it.type == "Cash In" }.sumOf { it.amount }
            val expensesTotal = expenses.filter { it.type == "Cash Out" }.sumOf { it.amount }
            totalIncome.value = income
            totalExpenses.value = expensesTotal
            totalBalance.value = income - expensesTotal
        }
        totalBalance.addSource(totalIncome) { income ->
            totalBalance.value = income - (totalExpenses.value ?: 0.0)
        }
        totalBalance.addSource(totalExpenses) { expensesTotal ->
            totalBalance.value = (totalIncome.value ?: 0.0) - expensesTotal
        }
        totalIncome.addSource(allExpenses) { expenses ->
            totalIncome.value = expenses.filter { it.type == "Cash In" }.sumOf { it.amount }
        }
        totalExpenses.addSource(allExpenses) { expenses ->
            totalExpenses.value = expenses.filter { it.type == "Cash Out" }.sumOf { it.amount }
        }
    }
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
