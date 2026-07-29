package com.buffalomilkpredictor.ui.screens.history

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import java.text.SimpleDateFormat
import java.util.*

data class HistoryItem(
    val id: Int,
    val breed: String,
    val date: String,
    val recommendation: String,
    val score: Float,
    val milkPrediction: String
)

@Composable
fun HistoryScreen(navController: NavController) {
    // Mock history data for demonstration
    val mockHistory = listOf(
        HistoryItem(1, "Murrah Buffalo", "Today at 2:30 PM", "Excellent", 0.88f, "22+ L"),
        HistoryItem(2, "Nili-Ravi Buffalo", "Yesterday at 10:15 AM", "Good", 0.76f, "18-22 L"),
        HistoryItem(3, "Mehsana Buffalo", "Dec 25, 2024", "Average", 0.62f, "14-18 L"),
        HistoryItem(4, "Surti Buffalo", "Dec 23, 2024", "Excellent", 0.84f, "22+ L"),
        HistoryItem(5, "Murrah Buffalo", "Dec 20, 2024", "Good", 0.70f, "18-22 L"),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header
        TopAppBar(
            title = { Text("Analysis History") },
            navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            actions = {
                IconButton(onClick = { }) {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }
                IconButton(onClick = { }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "More")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        )

        if (mockHistory.isEmpty()) {
            // Empty State
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.History,
                    contentDescription = null,
                    modifier = Modifier.size(80.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "No Analysis History",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Start analyzing buffalo to build your history",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            // History List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Stats Summary
                item {
                    StatsSummaryCard(mockHistory)
                }

                // History Items
                items(mockHistory) { item ->
                    HistoryItemCard(
                        item = item,
                        onClick = {
                            navController.navigate("result/${item.id}")
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun StatsSummaryCard(history: List<HistoryItem>) {
    val excellentCount = history.count { it.score >= 0.85 }
    val averageScore = history.map { it.score }.average()

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatItem(
                label = "Total Analyses",
                value = history.size.toString(),
                icon = Icons.Default.AssignmentTurnedIn,
                color = MaterialTheme.colorScheme.primary
            )
            StatItem(
                label = "Excellent Picks",
                value = excellentCount.toString(),
                icon = Icons.Default.CheckCircle,
                color = Color(0xFF4CAF50)
            )
            StatItem(
                label = "Avg Score",
                value = "${(averageScore * 100).toInt()}%",
                icon = Icons.Default.TrendingUp,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun StatItem(
    label: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = color
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@Composable
private fun HistoryItemCard(
    item: HistoryItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.CameraAlt,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = item.breed,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = item.date,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Scores Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ScoreBadge(
                    label = "Score",
                    value = "${(item.score * 100).toInt()}%",
                    score = item.score,
                    modifier = Modifier.weight(1f)
                )
                ScoreBadge(
                    label = "Recommendation",
                    value = item.recommendation,
                    modifier = Modifier.weight(1f)
                )
                ScoreBadge(
                    label = "Milk Pred.",
                    value = item.milkPrediction,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun ScoreBadge(
    label: String,
    value: String,
    score: Float? = null,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (score != null) {
        when {
            score >= 0.85 -> Color(0xFF4CAF50).copy(alpha = 0.1f)
            score >= 0.70 -> Color(0xFF8BC34A).copy(alpha = 0.1f)
            score >= 0.55 -> Color(0xFFFFC107).copy(alpha = 0.1f)
            else -> Color(0xFFFF9800).copy(alpha = 0.1f)
        }
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }

    val textColor = if (score != null) {
        when {
            score >= 0.85 -> Color(0xFF2E7D32)
            score >= 0.70 -> Color(0xFF558B2F)
            score >= 0.55 -> Color(0xFFF57F17)
            else -> Color(0xFFE65100)
        }
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    Surface(
        modifier = modifier,
        color = backgroundColor,
        shape = MaterialTheme.shapes.small
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.labelMedium,
                color = textColor
            )
        }
    }
}
