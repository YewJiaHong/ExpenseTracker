package com.jiho.expensetracker.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jiho.expensetracker.R
import com.jiho.expensetracker.viewmodel.ExpenseViewModel

@Composable
fun ExpenseScreen(viewModel: ExpenseViewModel) {
    val context = LocalContext.current
    val expenses by viewModel.allExpenses.observeAsState(emptyList())
    val totalExpenses by viewModel.totalExpense.observeAsState(0)
    val safeTotal = totalExpenses ?: 0

    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(
                    painter = painterResource(R.drawable.outline_add_24),
                    contentDescription = "Add Expense"
                )
            }
        },
    ) { padding ->
        Column {
            Text(
                text = "Total Expenses: RM $safeTotal",
                fontSize = 22.sp,
                modifier = Modifier.padding(16.dp)
            )
            LazyColumn (modifier = Modifier.fillMaxWidth()) {
                items(expenses) { exp ->
                    ExpenseItem(expense = exp, onDelete = {
                        //todo add another alert box, confirm delete
                        viewModel.deleteExpense(exp)
                    })
                }
            }
        }
    }

    if (showDialog) {
        AddExpenseDialog(
            onAdd = { title, amount, date, transactionType, description ->
                viewModel.addExpense(title, amount, date, transactionType, description)
                Toast.makeText(context, "Added Expense!", Toast.LENGTH_SHORT).show()
            },
            onDismiss = { showDialog = false },
            alert = { Toast.makeText(context, "Fill up all fields", Toast.LENGTH_SHORT).show() })
    }
}
