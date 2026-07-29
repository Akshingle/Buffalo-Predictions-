@file:OptIn(ExperimentalMaterial3Api::class)

package com.buffalomilkpredictor.ui.screens.result

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.buffalomilkpredictor.ui.navigation.NavigationScreen

@Composable
fun ResultScreen(navController: NavController, resultId: Int) {
    // Mock analysis result for demonstration
    val analysis = MockAnalysisResult(
        breed = "Murrah Buffalo",
        breedConfidence = 0.92f,
        milkPrediction = "22+ L/day",
        overallScore = 0.88f,
        recommendation = "Excellent",
        bodyFrameScore = 0.85f,
        udderScore = 0.92f,
        teatScore = 0.88f,
        legScore = 0.80f,
        diseaseDetected = false,
        ageEstimate = "3-4 years",
        isPregnant = false
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header
        TopAppBar(
            title = { Text("Analysis Results") },
            navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            actions = {
                IconButton(onClick = { }) {
                    Icon(Icons.Default.FileDownload, contentDescription = "Download PDF")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Overall Score Card
            item {
                ResultHeaderCard(analysis)
            }

            // Recommendation Card
            item {
                RecommendationCard(analysis)
            }

            // Scores Section
            item {
                SectionTitle("Detailed Scores")
            }

            item {
                ScoreRow("Breed Identification", analysis.breed, analysis.breedConfidence)
            }

            item {
                ScoreRow("Body Frame", score = analysis.bodyFrameScore)
            }

            item {
                ScoreRow("Udder Quality", score = analysis.udderScore)
            }

            item {
                ScoreRow("Teat Quality", score = analysis.teatScore)
            }

            item {
                ScoreRow("Leg Quality", score = analysis.legScore)
            }

            // Additional Information
            item {
                SectionTitle("Health & Characteristics")
            }

            item {
                HealthInfoCard(
                    diseaseDetected = analysis.diseaseDetected,
                    ageEstimate = analysis.ageEstimate,
                    isPregnant = analysis.isPregnant
                )
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        // Action Buttons
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = 8.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = { navController.navigate(NavigationScreen.Home.route) {
                        popUpTo(NavigationScreen.Home.route) { inclusive = true }
                    } },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                ) {
                    Icon(Icons.Default.Home, contentDescription = null, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Home")
                }

                Button(
                    onClick = { navController.navigate(NavigationScreen.History.route) },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    colors = ButtonDefaults.outlinedButtonColors(),
                    border = ButtonDefaults.outlinedButtonBorder
                ) {
                    Icon(Icons.Default.History, contentDescription = null, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("History")
                }
            }
        }
    }
}

@Composable
private fun ResultHeaderCard(analysis: MockAnalysisResult) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Overall Score",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = "${(analysis.overallScore * 100).toInt()}%",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = analysis.milkPrediction,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
private fun RecommendationCard(analysis: MockAnalysisResult) {
    val backgroundColor = when (analysis.recommendation) {
        "Excellent" -> Color(0xFF4CAF50)
        "Good" -> Color(0xFF8BC34A)
        "Average" -> Color(0xFFFFC107)
        "Below Average" -> Color(0xFFFF9800)
        else -> Color(0xFFF44336)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor.copy(alpha = 0.1f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                imageVector = when (analysis.recommendation) {
                    "Excellent", "Good" -> Icons.Default.CheckCircle
                    "Average" -> Icons.Default.WarningAmber
                    else -> Icons.Default.Cancel
                },
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = backgroundColor
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Buying Recommendation",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = analysis.recommendation,
                    style = MaterialTheme.typography.bodyLarge,
                    color = backgroundColor
                )
            }
        }
    }
}

@Composable
private fun ScoreRow(label: String, breedName: String? = null, score: Float) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = label, style = MaterialTheme.typography.bodyMedium)
                if (breedName != null) {
                    Text(
                        text = breedName,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "${(score * 100).toInt()}%",
                    style = MaterialTheme.typography.bodyMedium
                )
                LinearProgressIndicator(
                    progress = score,
                    modifier = Modifier
                        .width(80.dp)
                        .height(4.dp),
                    color = getScoreColor(score),
                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                )
            }
        }
    }
}

@Composable
private fun HealthInfoCard(
    diseaseDetected: Boolean,
    ageEstimate: String,
    isPregnant: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            HealthInfoRow(
                icon = Icons.Default.Healing,
                label = "Health Status",
                value = if (diseaseDetected) "Issues Detected" else "No Issues Detected",
                statusOk = !diseaseDetected
            )
            HealthInfoRow(
                icon = Icons.Default.Face,
                label = "Age Estimate",
                value = ageEstimate,
                statusOk = true
            )
            HealthInfoRow(
                icon = Icons.Default.Favorite,
                label = "Pregnancy Status",
                value = if (isPregnant) "Pregnant" else "Not Pregnant",
                statusOk = !isPregnant
            )
        }
    }
}

@Composable
private fun HealthInfoRow(
    icon: ImageVector,
    label: String,
    value: String,
    statusOk: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = if (statusOk)
                MaterialTheme.colorScheme.primary
            else
                Color(0xFFF44336)
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(text = label, style = MaterialTheme.typography.bodyMedium)
            Text(
                text = value,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

private fun getScoreColor(score: Float): Color {
    return when {
        score >= 0.85 -> Color(0xFF4CAF50)
        score >= 0.70 -> Color(0xFF8BC34A)
        score >= 0.55 -> Color(0xFFFFC107)
        score >= 0.40 -> Color(0xFFFF9800)
        else -> Color(0xFFF44336)
    }
}

data class MockAnalysisResult(
    val breed: String,
    val breedConfidence: Float,
    val milkPrediction: String,
    val overallScore: Float,
    val recommendation: String,
    val bodyFrameScore: Float,
    val udderScore: Float,
    val teatScore: Float,
    val legScore: Float,
    val diseaseDetected: Boolean,
    val ageEstimate: String,
    val isPregnant: Boolean
)
