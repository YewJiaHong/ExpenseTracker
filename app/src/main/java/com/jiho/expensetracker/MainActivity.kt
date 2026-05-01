package com.jiho.expensetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jiho.expensetracker.ui.ExpenseScreen
import com.jiho.expensetracker.ui.theme.ExpenseTrackerTheme
import com.jiho.expensetracker.viewmodel.ExpenseViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExpenseTrackerTheme {
                val viewModel: ExpenseViewModel = viewModel()
                ExpenseScreen(viewModel)
            }
        }
    }
}