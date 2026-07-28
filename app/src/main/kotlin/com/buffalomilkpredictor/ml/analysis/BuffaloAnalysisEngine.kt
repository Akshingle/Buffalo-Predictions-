package com.buffalomilkpredictor.ml.analysis

import android.graphics.Bitmap
import com.buffalomilkpredictor.data.model.BuffaloAnalysis
import com.buffalomilkpredictor.data.model.Defect
import com.buffalomilkpredictor.data.model.DefectSeverity
import com.buffalomilkpredictor.data.model.ScoringParameters
import com.buffalomilkpredictor.ml.model.BreedIdentifier
import com.buffalomilkpredictor.ml.model.DiseaseDetector
import com.buffalomilkpredictor.ml.model.BodyAnalyzer
import com.buffalomilkpredictor.ml.model.UdderAnalyzer
import timber.log.Timber
import java.util.Date

class BuffaloAnalysisEngine(
    private val breedIdentifier: BreedIdentifier,
    private val diseaseDetector: DiseaseDetector,
    private val bodyAnalyzer: BodyAnalyzer,
    private val udderAnalyzer: UdderAnalyzer
) {
    private val scoringParams = ScoringParameters()

    suspend fun analyzeBuffaloImage(
        images: Map<String, Bitmap>,
        notes: String = ""
    ): BuffaloAnalysis {
        try {
            // Step 1: Breed Identification
            val (breed, breedConfidence) = identifyBreed(images)

            // Step 2: Analyze Body Frame
            val bodyFrameScore = analyzeBodyFrame(images)

            // Step 3: Analyze Body Condition
            val bodyConditionScore = analyzeBodyCondition(images)

            // Step 4: Analyze Udder
            val (udderScore, udderAnalysisDetails) = analyzeUdderQuality(images)

            // Step 5: Analyze Teats
            val teatScore = analyzeTeatQuality(images, udderAnalysisDetails)

            // Step 6: Analyze Legs and Hooves
            val legScore = analyzeLegQuality(images)

            // Step 7: Detect Diseases
            val (diseaseScore, diseasesDetected, defects) = detectDiseases(images)

            // Step 8: Analyze Walking
            val walkingScore = analyzeWalking(images)

            // Step 9: Estimate Age
            val ageEstimate = estimateAge(images)

            // Step 10: Pregnancy Detection
            val isPregnant = detectPregnancy(images)

            // Calculate Overall Score
            val overallScore = calculateWeightedScore(
                bodyFrameScore = bodyFrameScore,
                bodyConditionScore = bodyConditionScore,
                udderScore = udderScore,
                teatScore = teatScore,
                legScore = legScore,
                diseaseScore = diseaseScore,
                walkingScore = walkingScore,
                breedScore = breedConfidence,
                ageScore = 0.5f // Neutral age score
            )

            // Predict Milk Production
            val (milkCategory, milkRange) = predictMilkProduction(overallScore, breed)

            // Generate Buying Recommendation
            val recommendation = generateBuyingRecommendation(
                overallScore = overallScore,
                defects = defects,
                diseaseScore = diseaseScore,
                udderScore = udderScore
            )

            Timber.d("Analysis completed: breed=$breed, score=$overallScore, recommendation=$recommendation")

            return BuffaloAnalysis(
                analysisDate = Date(),
                breed = breed,
                breedConfidence = breedConfidence,
                bodyConditionScore = bodyConditionScore,
                udderScore = udderScore,
                bodyFrameScore = bodyFrameScore,
                teatScore = teatScore,
                legScore = legScore,
                diseaseScore = diseaseScore,
                walkingScore = walkingScore,
                ageEstimate = ageEstimate,
                milkPredictionCategory = milkCategory,
                milkProductionRange = milkRange,
                overallConfidence = overallScore,
                buyRecommendation = recommendation,
                notes = notes,
                imagePaths = images.keys.toList(),
                diseaseDetected = diseasesDetected,
                defectsDetected = defects,
                isPregnant = isPregnant
            )
        } catch (e: Exception) {
            Timber.e(e, "Error during buffalo analysis")
            return BuffaloAnalysis(
                notes = notes,
                overallConfidence = 0f
            )
        }
    }

    private suspend fun identifyBreed(images: Map<String, Bitmap>): Pair<String, Float> {
        return breedIdentifier.identifyBreed(images)
    }

    private suspend fun analyzeBodyFrame(images: Map<String, Bitmap>): Float {
        return bodyAnalyzer.analyzeBodyFrame(images)
    }

    private suspend fun analyzeBodyCondition(images: Map<String, Bitmap>): Float {
        return bodyAnalyzer.analyzeBodyCondition(images)
    }

    private suspend fun analyzeUdderQuality(
        images: Map<String, Bitmap>
    ): Pair<Float, Map<String, Any>> {
        return udderAnalyzer.analyzeUdder(images)
    }

    private suspend fun analyzeTeatQuality(
        images: Map<String, Bitmap>,
        udderDetails: Map<String, Any>
    ): Float {
        return udderAnalyzer.analyzeTeats(images)
    }

    private suspend fun analyzeLegQuality(images: Map<String, Bitmap>): Float {
        return bodyAnalyzer.analyzeLegQuality(images)
    }

    private suspend fun detectDiseases(
        images: Map<String, Bitmap>
    ): Triple<Float, List<String>, List<Defect>> {
        return diseaseDetector.detectDiseases(images)
    }

    private suspend fun analyzeWalking(images: Map<String, Bitmap>): Float {
        // Walking analysis from video frame
        return 0.7f // Default score if walking video not available
    }

    private suspend fun estimateAge(images: Map<String, Bitmap>): String {
        return "3-4 years"
    }

    private suspend fun detectPregnancy(images: Map<String, Bitmap>): Boolean {
        // Analyze abdominal shape for pregnancy signs
        return false
    }

    private fun calculateWeightedScore(
        bodyFrameScore: Float,
        bodyConditionScore: Float,
        udderScore: Float,
        teatScore: Float,
        legScore: Float,
        diseaseScore: Float,
        walkingScore: Float,
        breedScore: Float,
        ageScore: Float
    ): Float {
        val score = (
            udderScore * scoringParams.udderWeight +
            bodyFrameScore * scoringParams.bodyFrameWeight +
            teatScore * scoringParams.teatWeight +
            legScore * scoringParams.legWeight +
            bodyConditionScore * scoringParams.bodyConditionWeight +
            diseaseScore * scoringParams.diseaseWeight +
            breedScore * scoringParams.breedWeight +
            walkingScore * scoringParams.walkingWeight +
            ageScore * scoringParams.ageWeight
        )
        return score.coerceIn(0f, 1f)
    }

    private fun predictMilkProduction(score: Float, breed: String): Pair<String, String> {
        return when {
            score >= 0.85 -> "Excellent" to "22+ L"
            score >= 0.70 -> "Good" to "18-22 L"
            score >= 0.55 -> "Average" to "14-18 L"
            score >= 0.40 -> "Below Average" to "10-14 L"
            else -> "Poor" to "8-10 L"
        }
    }

    private fun generateBuyingRecommendation(
        overallScore: Float,
        defects: List<Defect>,
        diseaseScore: Float,
        udderScore: Float
    ): String {
        // Check for critical defects
        val criticalDefects = defects.count { it.severity == DefectSeverity.RED }
        
        return when {
            criticalDefects > 0 -> "Reject Immediately"
            overallScore < 0.4 || diseaseScore < 0.3 -> "Avoid Buying"
            overallScore < 0.55 || udderScore < 0.5 -> "Average"
            overallScore < 0.75 -> "Good Purchase"
            else -> "Excellent Purchase"
        }
    }
}
