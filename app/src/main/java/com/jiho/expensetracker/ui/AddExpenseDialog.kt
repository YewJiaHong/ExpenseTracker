package com.jiho.expensetracker.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.jiho.expensetracker.R
import com.jiho.expensetracker.enums.TransactionType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Objects

@Composable
fun AddExpenseDialog(
    onAdd: (String, String, Long, TransactionType, String) -> Unit,
    onDismiss: () -> Unit,
    alert: ()->Unit
) {
    var title by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }

    val datePickerState = rememberDatePickerState()
    val date = datePickerState.selectedDateMillis ?: System.currentTimeMillis()

    var description by remember { mutableStateOf("") }
    var transactionType by remember { mutableStateOf<TransactionType?>(null) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(onClick = {
                if (listOf(
                        title,
                        amount,
                        transactionType,
                        description
                    ).any {
                        if (it is String){
                            it.isBlank()
                        } else{
                            Objects.isNull(it)
                        }
                    }
                ) {
                    alert()
                    return@Button
                }
                onAdd(title, amount, date, transactionType!!, description)
                onDismiss()
            }) {
                Text("Add")
            }
        },
        title = { Text("Add Expense") },
        text = {
            Column {
                CreateTextFieldWithValue(
                    title,
                    onValueChange = { title = it },
                    "Title")


                CreateTextFieldWithValue(
                    amount,
                    onValueChange = {
                        if (it.all { it.isDigit() }) {
                            amount = it
                        }
                    }, "Amount",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                CreateTextFieldWithValue(
                    description,
                    onValueChange = { description = it },
                    "Description"
                )

                ShowDropDownMenu(onMenuSelected = {transactionType = it})

                ShowDatePicker(datePickerState, date)
            }
        }
    )
}

@Composable
fun CreateTextFieldWithValue(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        keyboardOptions = keyboardOptions
    )
}

@Composable
fun ShowDropDownMenu(onMenuSelected: (TransactionType?)->Unit){
    var selectedType by remember { mutableStateOf<TransactionType?>(null) }
    var showDropDown by remember {mutableStateOf(false)}

    Box(
        contentAlignment = Alignment.TopStart
    ){
        OutlinedTextField(
            value = selectedType?.code ?: "",
            onValueChange = {onMenuSelected(selectedType)},
            label = {Text("Transaction Type")},
            readOnly = true,
            trailingIcon = {
                IconButton(
                    onClick = {showDropDown = !showDropDown}
                ) {
                    Icon(Icons.Filled.Menu, contentDescription = "Transaction Type List")
                }
            }
        )

        if (showDropDown){
            DropdownMenu(
                expanded = showDropDown,
                onDismissRequest = {showDropDown=false},
            ) {
                TransactionType.entries.forEach{type->
                    CreateDropDownMenuItem(
                        type,
                        onItemSelected = {
                            showDropDown=false
                            selectedType = it
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CreateDropDownMenuItem(transactionType: TransactionType, onItemSelected: (TransactionType)->Unit){
    DropdownMenuItem(
        text = { Text(transactionType.code) },
        onClick = { onItemSelected(transactionType) },
    )
}

/**
 * Returns the selected date by assigning the date in millis to the argument selectedDate
 */
@Composable
fun ShowDatePicker(datePickerState: DatePickerState, selectedDate: Long) {
    var showDatePicker by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = convertMillisToDate(selectedDate),
            onValueChange = { },
            label = { Text("Date Of Transaction") },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { showDatePicker = !showDatePicker }) {
                    Icon(
                        painter = painterResource(R.drawable.outline_ad_24),
                        contentDescription = "Select date of transaction"
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
        )

        if (showDatePicker) {
            Popup(
                onDismissRequest = { showDatePicker = false },
                alignment = Alignment.TopStart
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = 64.dp)
                        .shadow(elevation = 4.dp)
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(16.dp)
                ) {
                    DatePicker(
                        state = datePickerState,
                        showModeToggle = false
                    )
                }
            }
        }
    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}