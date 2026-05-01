package com.jiho.expensetracker.repository

import androidx.lifecycle.LiveData
import com.jiho.expensetracker.data.Expense
import com.jiho.expensetracker.data.ExpenseDAO

class ExpenseRepository(private val dao: ExpenseDAO) {
    val allExpenses: LiveData<List<Expense>> = dao.getAllExpenses()
    val totalExpense: LiveData<Double> = dao.getTotalExpenses()

    suspend fun insertExpense(expense: Expense){
        dao.insertExpense(expense)
    }

    suspend fun deleteExpense(expense: Expense){
        dao.deleteExpense(expense)
    }

}