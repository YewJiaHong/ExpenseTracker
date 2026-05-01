package com.jiho.expensetracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.jiho.expensetracker.data.Expense
import com.jiho.expensetracker.data.ExpenseDatabase
import com.jiho.expensetracker.enums.TransactionType
import com.jiho.expensetracker.repository.ExpenseRepository
import kotlinx.coroutines.launch

class ExpenseViewModel(application: Application): AndroidViewModel(application) {
    private val repository: ExpenseRepository
    val allExpenses: LiveData<List<Expense>>
    val totalExpense:LiveData<Double>

    init{
        val dao = ExpenseDatabase.getDatabase(application).expenseDAO()
        repository = ExpenseRepository(dao)
        allExpenses = repository.allExpenses
        totalExpense = repository.totalExpense
    }

    fun addExpense(title: String, amount: String, date: Long, type: TransactionType, description: String){
        viewModelScope.launch{
            repository.insertExpense(
                Expense(title = title, amount = amount, dateMilis = date, typeOfTransaction = type, description = description)
            )
        }
    }

    fun deleteExpense(expense: Expense){
        viewModelScope.launch {
            repository.deleteExpense(expense)
        }
    }
}