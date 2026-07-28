package com.buffalomilkpredictor.data.repository

import com.buffalomilkpredictor.data.database.BuffaloAnalysisDao
import com.buffalomilkpredictor.data.model.BuffaloAnalysis
import com.buffalomilkpredictor.data.model.BuffaloAnalysisEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BuffaloAnalysisRepository(private val dao: BuffaloAnalysisDao) {

    fun getAllAnalysisFlow(): Flow<List<BuffaloAnalysis>> {
        return dao.getAllAnalysisFlow().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    suspend fun getAnalysisById(id: Int): BuffaloAnalysis? {
        return dao.getAnalysisById(id)?.toDomain()
    }

    suspend fun insertAnalysis(analysis: BuffaloAnalysis): Long {
        return dao.insert(analysis.toEntity())
    }

    suspend fun updateAnalysis(analysis: BuffaloAnalysis) {
        dao.update(analysis.toEntity())
    }

    suspend fun deleteAnalysis(analysis: BuffaloAnalysis) {
        dao.delete(analysis.toEntity())
    }

    suspend fun deleteAnalysisById(id: Int) {
        dao.deleteById(id)
    }

    fun getAnalysisByBreed(breed: String): Flow<List<BuffaloAnalysis>> {
        return dao.getAnalysisByBreed(breed).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    suspend fun getAnalysisByDateRange(startDate: Long, endDate: Long): List<BuffaloAnalysis> {
        return dao.getAnalysisByDateRange(startDate, endDate).map { it.toDomain() }
    }

    suspend fun getTotalCount(): Int {
        return dao.getTotalCount()
    }

    suspend fun getAnalysisPaginated(limit: Int, offset: Int): List<BuffaloAnalysis> {
        return dao.getAnalysisPaginated(limit, offset).map { it.toDomain() }
    }
}

private fun BuffaloAnalysisEntity.toDomain(): BuffaloAnalysis {
    return BuffaloAnalysis(
        id = id,
        analysisDate = java.util.Date(analysisDate),
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
        milkPredictionCategory = milkPredictionCategory,
        milkProductionRange = milkProductionRange,
        overallConfidence = overallConfidence,
        buyRecommendation = buyRecommendation,
        notes = notes,
        imagePaths = imagePaths.split(",").filter { it.isNotEmpty() },
        diseaseDetected = diseaseDetected.split(",").filter { it.isNotEmpty() },
        isPregnant = isPregnant,
        estimatedAge = estimatedAge
    )
}

private fun BuffaloAnalysis.toEntity(): BuffaloAnalysisEntity {
    return BuffaloAnalysisEntity(
        id = id,
        analysisDate = analysisDate.time,
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
        milkPredictionCategory = milkPredictionCategory,
        milkProductionRange = milkProductionRange,
        overallConfidence = overallConfidence,
        buyRecommendation = buyRecommendation,
        notes = notes,
        imagePaths = imagePaths.joinToString(","),
        diseaseDetected = diseaseDetected.joinToString(",")
    )
}
