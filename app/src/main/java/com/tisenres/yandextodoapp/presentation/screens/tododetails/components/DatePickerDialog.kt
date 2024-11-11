package com.tisenres.yandextodoapp.presentation.screens.tododetails.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import java.util.Calendar
import java.util.Date

@Composable
fun CustomDatePickerDialog(
    initialDate: Date,
    onDateSelected: (Date) -> Unit,
    onDismissRequest: () -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance().apply { time = initialDate }
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    LaunchedEffect(Unit) {
        val datePickerDialog = android.app.DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDay ->
                calendar.set(selectedYear, selectedMonth, selectedDay)
                onDateSelected(calendar.time)
            },
            year, month, day
        )

        datePickerDialog.setOnDismissListener { onDismissRequest() }

        datePickerDialog.show()
    }
}

@Preview(showBackground = true)
@Composable
fun CustomDatePickerDialogPreview() {
    var isDatePickerVisible by remember { mutableStateOf(true) }
    var selectedDate by remember { mutableStateOf(Date()) }

    if (isDatePickerVisible) {
        CustomDatePickerDialog(
            initialDate = selectedDate,
            onDateSelected = { date ->
                selectedDate = date
                isDatePickerVisible = false
            },
            onDismissRequest = {
                isDatePickerVisible = false
            }
        )
    }
}
