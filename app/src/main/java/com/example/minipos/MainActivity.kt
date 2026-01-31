package com.example.minipos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.minipos.data.AppDatabase
import com.example.minipos.data.TransactionRepository
import com.example.minipos.ui.navigation.AppNavigation
import com.example.minipos.ui.theme.MiniposTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        val database = AppDatabase.getDatabase(applicationContext)
        val repository = TransactionRepository(database.transactionDao())

        setContent {
            MiniposTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Passing modifier with padding to AppNavigation isn't strictly necessary 
                    // if screens handle it, but good practice to respect edge-to-edge.
                    // However, our AppNavigation doesn't take modifier. 
                    // Let's wrap it in a Box or adjust AppNavigation. 
                    // For simplicity, we'll just ignore the padding here or pass it if extended.
                    // Actually, let's just use a Box padding.
                    androidx.compose.foundation.layout.Box(modifier = Modifier.padding(innerPadding)) {
                        AppNavigation(repository = repository)
                    }
                }
            }
        }
    }
}