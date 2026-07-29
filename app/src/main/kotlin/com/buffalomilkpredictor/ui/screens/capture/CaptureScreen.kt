package com.buffalomilkpredictor.ui.screens.capture

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.buffalomilkpredictor.ui.navigation.NavigationScreen

data class CaptureAngle(
    val name: String,
    val description: String,
    val icon: ImageVector,
    val required: Boolean = true,
    var isCapture: Boolean = false
)

@Composable
fun CaptureScreen(navController: NavController) {
    val captureAngles = remember {
        mutableStateOf(listOf(
            CaptureAngle("Front View", "Full front of the buffalo", Icons.Default.FaceRetouchingNatural, true),
            CaptureAngle("Side View", "Left or right side profile", Icons.Default.ViewDay, true),
            CaptureAngle("Rear View", "Back view of buffalo", Icons.Default.ViewAgenda, true),
            CaptureAngle("Udder", "Close-up of udder", Icons.Default.ImageAspectRatio, true),
            CaptureAngle("Teats", "Close-up of teats", Icons.Default.TouchApp, true),
            CaptureAngle("Legs", "Front and hind legs", Icons.Default.TrackChanges, false),
            CaptureAngle("Face", "Head and face detail", Icons.Default.Face, false),
            CaptureAngle("Milk Veins", "Abdominal milk veins", Icons.Default.LineWeight, false)
        ))
    }

    val notesText = remember { mutableStateOf("") }
    val selectedCount = captureAngles.value.count { it.isCapture }
    val requiredCount = captureAngles.value.count { it.required }
    val allRequiredCaptured = captureAngles.value.filter { it.required }.all { it.isCapture }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header
        TopAppBar(
            title = { Text("Capture Buffalo Images") },
            navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
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
            // Progress Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Capture Progress",
                            style = MaterialTheme.typography.titleMedium
                        )
                        LinearProgressIndicator(
                            progress = selectedCount.toFloat() / requiredCount,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp),
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "$selectedCount of $requiredCount required images captured",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Capture Angles
            items(captureAngles.value) { angle ->
                CaptureAngleCard(
                    angle = angle,
                    onToggle = {
                        captureAngles.value = captureAngles.value.map {
                            if (it.name == angle.name) it.copy(isCapture = !it.isCapture) else it
                        }
                    }
                )
            }

            // Notes Section
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Additional Notes (Optional)",
                            style = MaterialTheme.typography.titleMedium
                        )
                        TextField(
                            value = notesText.value,
                            onValueChange = { notesText.value = it },
                            placeholder = { Text("Add any observations...") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp),
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = MaterialTheme.colorScheme.surface
                            ),
                            maxLines = 4
                        )
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(8.dp)) }
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
                    onClick = { navController.navigateUp() },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    colors = ButtonDefaults.outlinedButtonColors(),
                    border = ButtonDefaults.outlinedButtonBorder
                ) {
                    Text("Cancel")
                }

                Button(
                    onClick = {
                        if (allRequiredCaptured) {
                            navController.navigate(NavigationScreen.Analysis.route)
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    enabled = allRequiredCaptured
                ) {
                    Text("Analyze")
                }
            }
        }
    }
}

@Composable
private fun CaptureAngleCard(
    angle: CaptureAngle,
    onToggle: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (angle.isCapture)
                MaterialTheme.colorScheme.primaryContainer
            else
                MaterialTheme.colorScheme.surface
        ),
        border = if (angle.isCapture) null else CardDefaults.outlinedCardBorder()
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = angle.icon,
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = if (angle.isCapture)
                    MaterialTheme.colorScheme.onPrimaryContainer
                else
                    MaterialTheme.colorScheme.onSurface
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = angle.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = if (angle.isCapture)
                        MaterialTheme.colorScheme.onPrimaryContainer
                    else
                        MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = angle.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (angle.isCapture)
                        MaterialTheme.colorScheme.onPrimaryContainer
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
                if (!angle.required) {
                    Text(
                        text = "Optional",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Checkbox(
                checked = angle.isCapture,
                onCheckedChange = { onToggle() },
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
