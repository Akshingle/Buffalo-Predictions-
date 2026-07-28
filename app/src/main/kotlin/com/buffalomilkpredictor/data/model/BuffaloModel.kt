package com.buffalomilkpredictor.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "buffalo_analysis")
data class BuffaloAnalysisEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val analysisDate: Long = Date().time,
    val breed: String = "Unknown",
    val breedConfidence: Float = 0f,
    val bodyConditionScore: Float = 0f,
    val udderScore: Float = 0f,
    val bodyFrameScore: Float = 0f,
    val teatScore: Float = 0f,
    val legScore: Float = 0f,
    val diseaseScore: Float = 0f,
    val walkingScore: Float = 0f,
    val ageEstimate: String = "Unknown",
    val milkPredictionCategory: String = "Unknown",
    val milkProductionRange: String = "8-10 L",
    val overallConfidence: Float = 0f,
    val buyRecommendation: String = "Avoid Buying",
    val notes: String = "",
    val imagePaths: String = "", // JSON array of image paths
    val diseaseDetected: String = "",
    val defectsDetected: String = "", // JSON array of defects
    val isPregnant: Boolean = false,
    val estimatedAge: Int = 0
)

data class BuffaloAnalysis(
    val id: Int = 0,
    val analysisDate: Date = Date(),
    val breed: String = "Unknown",
    val breedConfidence: Float = 0f,
    val bodyConditionScore: Float = 0f,
    val udderScore: Float = 0f,
    val bodyFrameScore: Float = 0f,
    val teatScore: Float = 0f,
    val legScore: Float = 0f,
    val diseaseScore: Float = 0f,
    val walkingScore: Float = 0f,
    val ageEstimate: String = "Unknown",
    val milkPredictionCategory: String = "Unknown",
    val milkProductionRange: String = "8-10 L",
    val overallConfidence: Float = 0f,
    val buyRecommendation: String = "Avoid Buying",
    val notes: String = "",
    val imagePaths: List<String> = emptyList(),
    val diseaseDetected: List<String> = emptyList(),
    val defectsDetected: List<Defect> = emptyList(),
    val isPregnant: Boolean = false,
    val estimatedAge: Int = 0
)

data class Defect(
    val name: String,
    val severity: DefectSeverity,
    val location: String,
    val boundingBox: BoundingBox? = null
)

data class BoundingBox(
    val x: Float,
    val y: Float,
    val width: Float,
    val height: Float
)

enum class DefectSeverity {
    RED,   // Critical - Reject immediately
    YELLOW, // Warning - Avoid or investigate
    GREEN   // Minor - Acceptable
}

data class ScoringParameters(
    val udderWeight: Float = 0.35f,
    val bodyFrameWeight: Float = 0.15f,
    val teatWeight: Float = 0.15f,
    val legWeight: Float = 0.10f,
    val bodyConditionWeight: Float = 0.10f,
    val diseaseWeight: Float = 0.10f,
    val breedWeight: Float = 0.05f,
    val walkingWeight: Float = 0.05f,
    val ageWeight: Float = 0.05f
)

data class BreedCharacteristics(
    val name: String,
    val idealMilkProduction: String,
    val bodyTraits: List<String>,
    val udderTraits: List<String>,
    val colorPattern: List<String>,
    val averageWeight: String,
    val averageHeight: String
)
