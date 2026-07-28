package com.buffalomilkpredictor.ui.screens.analysis

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun AnalysisScreen(navController: NavController) {
    val analysisProgress = remember { mutableStateOf(0) }
    val analysisStage = remember { mutableStateOf("Initializing...") }
    val isComplete = remember { mutableStateOf(false) }

    val stages = listOf(
        "Initializing AI models...",
        "Analyzing breed characteristics...",
        "Evaluating body frame...",
        "Assessing udder quality...",
        "Analyzing teat structure...",
        "Evaluating leg and hoof quality...",
        "Detecting health issues...",
        "Analyzing walking pattern...",
        "Estimating age...",
        "Calculating milk production...",
        "Generating recommendations...",
        "Finalizing report..."
    )

    LaunchedEffect(Unit) {
        stages.forEachIndexed { index, stage ->
            analysisStage.value = stage
            analysisProgress.value = ((index + 1) * 100) / stages.size
            delay(800)
        }
        isComplete.value = true
        delay(1000)
        navController.navigate("result/1") {
            popUpTo("capture") { inclusive = true }
        }
    }

    val rotation = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        while (!isComplete.value) {
            rotation.animateTo(
                targetValue = 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(2000, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                )
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Loading Animation
        Box(
            modifier = Modifier.size(120.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(120.dp)
                    .rotate(rotation.value),
                color = MaterialTheme.colorScheme.primary,
                strokeWidth = 4.dp
            )
            Icon(
                imageVector = Icons.Default.AutoAwesome,
                contentDescription = null,
                modifier = Modifier.size(50.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Progress Percentage
        Text(
            text = "${analysisProgress.value}%",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Progress Bar
        LinearProgressIndicator(
            progress = analysisProgress.value.toFloat() / 100f,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(8.dp),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Status Text
        Text(
            text = "Analyzing Buffalo...",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Current Stage
        Text(
            text = analysisStage.value,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(0.85f)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Info Card
        Card(
            modifier = Modifier.fillMaxWidth(0.9f),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Using AI & Computer Vision",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Text(
                    text = "Analyzing 17 dairy selection parameters including breed, body frame, udder quality, health indicators, and more.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        if (isComplete.value) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Analysis Complete! Preparing results...",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )
        }
    }
}
