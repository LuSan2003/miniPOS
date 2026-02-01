package com.example.minipos.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.minipos.ui.viewmodel.PaymentState
import com.example.minipos.ui.viewmodel.PaymentViewModel
import kotlinx.coroutines.delay

@Composable
fun PaymentScreen(viewModel: PaymentViewModel, onNavigateToHistory: () -> Unit) {
    var amountInput by remember { mutableStateOf("") }
    val paymentState by viewModel.paymentState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Mini POS - Pago", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = amountInput,
            onValueChange = { amountInput = it },
            label = { Text("Monto") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val amount = amountInput.toDoubleOrNull() ?: 0.0
                viewModel.processPayment(amount)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Pagar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (val state = paymentState) {
            is PaymentState.Success -> {
                Text(text = state.message, color = MaterialTheme.colorScheme.primary)
                LaunchedEffect(state) {
                    amountInput = ""
                    delay(10000) //10 s
                    viewModel.resetState()
                }
            }
            is PaymentState.Error -> {
                Text(text = state.message, color = MaterialTheme.colorScheme.error)
                LaunchedEffect(state) {
                    delay(10000) //10s
                    viewModel.resetState()
                }
            }
            PaymentState.Loading -> {
                Text(text = "Procesando...")
            }
            else -> {}
        }

        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = onNavigateToHistory,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ver Historial")
        }
    }
}
