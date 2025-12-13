package com.example.smartpot.ui.components.control

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties

@Composable
fun IntControl(title: String, unit: String, value: Int, onValueChange: (Int) -> Unit) {
    var showPopup by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { showPopup = true },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(title, fontSize = 18.sp)
            Text("$value$unit", fontSize = 14.sp)
        }
    }

    if (showPopup) {
        Dialog(
            onDismissRequest = { showPopup = false }
        ) {
            Surface(
                tonalElevation = 4.dp,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                var textState by remember { mutableStateOf(TextFieldValue(value.toString())) }

                Column(modifier = Modifier.padding(12.dp)) {
                    Text(text = "$title ($unit)", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = textState,
                        onValueChange = { newTf ->
                            val filtered = newTf.text.filterIndexed { i, ch ->
                                (ch.isDigit()) || (ch == '-' && i == 0)
                            }
                            textState = newTf.copy(text = filtered)
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(12.dp))
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextButton(onClick = { showPopup = false }) {
                            Text("Отмена")
                        }
                        Spacer(Modifier.width(8.dp))
                        Button(onClick = {
                            val parsed = textState.text.toIntOrNull() ?: 0
                            onValueChange(parsed)
                            showPopup = false
                        }) {
                            Text("ОК")
                        }
                    }
                }
            }
        }
    }
}