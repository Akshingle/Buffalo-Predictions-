package com.buffalomilkpredictor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.buffalomilkpredictor.di.ServiceLocator
import com.buffalomilkpredictor.ui.navigation.AppNavigation
import com.buffalomilkpredictor.ui.theme.BuffaloMilkPredictorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize all services
        ServiceLocator.initializeServices(this)
        
        setContent {
            BuffaloMilkPredictorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}
