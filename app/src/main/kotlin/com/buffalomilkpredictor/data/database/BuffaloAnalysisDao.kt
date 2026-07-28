package com.buffalomilkpredictor.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.buffalomilkpredictor.data.model.BuffaloAnalysisEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BuffaloAnalysisDao {
    @Insert
    suspend fun insert(analysis: BuffaloAnalysisEntity): Long

    @Update
    suspend fun update(analysis: BuffaloAnalysisEntity)

    @Delete
    suspend fun delete(analysis: BuffaloAnalysisEntity)

    @Query("SELECT * FROM buffalo_analysis WHERE id = :id")
    suspend fun getAnalysisById(id: Int): BuffaloAnalysisEntity?

    @Query("SELECT * FROM buffalo_analysis ORDER BY analysisDate DESC")
    fun getAllAnalysisFlow(): Flow<List<BuffaloAnalysisEntity>>

    @Query("SELECT * FROM buffalo_analysis ORDER BY analysisDate DESC LIMIT :limit OFFSET :offset")
    suspend fun getAnalysisPaginated(limit: Int, offset: Int): List<BuffaloAnalysisEntity>

    @Query("SELECT COUNT(*) FROM buffalo_analysis")
    suspend fun getTotalCount(): Int

    @Query("SELECT * FROM buffalo_analysis WHERE breed LIKE '%' || :breed || '%' ORDER BY analysisDate DESC")
    fun getAnalysisByBreed(breed: String): Flow<List<BuffaloAnalysisEntity>>

    @Query("DELETE FROM buffalo_analysis WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT * FROM buffalo_analysis WHERE analysisDate BETWEEN :startDate AND :endDate ORDER BY analysisDate DESC")
    suspend fun getAnalysisByDateRange(startDate: Long, endDate: Long): List<BuffaloAnalysisEntity>
}
