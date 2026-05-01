package com.jiho.expensetracker.ui


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jiho.expensetracker.R
import com.jiho.expensetracker.data.Expense
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Locale

@Composable
fun ExpenseItem(expense: Expense, onDelete: () -> Unit) {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = expense.title, fontSize = 20.sp)
                Text(text = "RM ${expense.amount}", fontSize = 16.sp)
                Text(
                    text = formatter.format(expense.dateMilis),
                    fontSize = 14.sp
                )
                Text(text = expense.typeOfTransaction.code, fontSize = 14.sp)
                Text(text = expense.description, fontSize = 14.sp)
            }
            IconButton(onClick = onDelete) {
                Icon(
                    painter = painterResource(R.drawable.baseline_delete_24),
                    contentDescription = "Delete"
                )
            }
        }
    }
}