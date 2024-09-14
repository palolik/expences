package com.kotlin5.expenses.model.repository

import androidx.lifecycle.LiveData
import com.kotlin5.expenses.model.db.Expense
import com.kotlin5.expenses.model.db.ExpenseDao
import javax.inject.Inject

class ExpenseRepository @Inject constructor(private val expenseDao: ExpenseDao) {

    fun getAllExpenses(): LiveData<List<Expense>> = expenseDao.getAllExpenses()

    suspend fun insertExpense(expense: Expense):Boolean{
        try {
            expenseDao.insertExpense(expense)
            return true
        }catch (e:Exception){
            return false
        }

    }
    suspend fun updateExpense(expense: Expense) = expenseDao.updateExpense(expense)
    suspend fun deleteExpense(expense: Expense) = expenseDao.deleteExpense(expense)
    suspend fun getExpenseById(id: Int): Expense? = expenseDao.getExpenseById(id)

}