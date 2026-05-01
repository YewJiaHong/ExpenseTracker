package com.jiho.expensetracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jiho.expensetracker.enums.TransactionType

@Entity(tableName = "expenses")
data class Expense(
    @PrimaryKey(true)
    val id: Int = 0,
    val title: String,
    val amount: String,
    val dateMilis: Long,
    val typeOfTransaction: TransactionType,
    val description: String
)
