package com.kotlin5.expenses

import androidx.lifecycle.LiveData
import javax.inject.Inject

class ExpenseRepository @Inject constructor(private val expenseDao: ExpenseDao) {

    fun getAllExpenses(): LiveData<List<Expense>> = expenseDao.getAllExpenses()

    suspend fun insertExpense(expense: Expense) = expenseDao.insertExpense(expense)
    suspend fun updateExpense(expense: Expense) = expenseDao.updateExpense(expense)
    suspend fun deleteExpense(expense: Expense) = expenseDao.deleteExpense(expense)
    suspend fun getExpenseById(id: Int): Expense? = expenseDao.getExpenseById(id)

}