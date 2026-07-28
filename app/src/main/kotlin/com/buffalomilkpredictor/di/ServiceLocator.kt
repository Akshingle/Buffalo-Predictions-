package com.buffalomilkpredictor.di

import android.content.Context
import com.buffalomilkpredictor.data.database.BuffaloDatabase
import com.buffalomilkpredictor.data.repository.BuffaloAnalysisRepository
import com.buffalomilkpredictor.ml.analysis.BuffaloAnalysisEngine
import com.buffalomilkpredictor.ml.model.BreedIdentifier
import com.buffalomilkpredictor.ml.model.BodyAnalyzer
import com.buffalomilkpredictor.ml.model.DiseaseDetector
import com.buffalomilkpredictor.ml.model.UdderAnalyzer
import com.buffalomilkpredictor.utils.camera.CameraManager
import com.buffalomilkpredictor.utils.image.ImageProcessor
import com.buffalomilkpredictor.utils.localization.LocalizationManager
import com.buffalomilkpredictor.utils.pdf.PDFReportGenerator
import com.buffalomilkpredictor.utils.preferences.PreferencesManager

/**
 * Simple Service Locator for dependency injection.
 * In a production app, consider using a full DI framework like Hilt or Koin.
 */
object ServiceLocator {
    private var database: BuffaloDatabase? = null
    private var repository: BuffaloAnalysisRepository? = null
    private var analysisEngine: BuffaloAnalysisEngine? = null
    
    private var cameraManager: CameraManager? = null
    private var imageProcessor: ImageProcessor? = null
    private var pdfGenerator: PDFReportGenerator? = null
    private var localizationManager: LocalizationManager? = null
    private var preferencesManager: PreferencesManager? = null

    fun initializeServices(context: Context) {
        // Database
        if (database == null) {
            database = BuffaloDatabase.getInstance(context)
        }

        // Repository
        if (repository == null) {
            repository = BuffaloAnalysisRepository(database!!.buffaloAnalysisDao())
        }

        // ML Models
        val breedIdentifier = BreedIdentifier()
        val diseaseDetector = DiseaseDetector()
        val bodyAnalyzer = BodyAnalyzer()
        val udderAnalyzer = UdderAnalyzer()

        // Analysis Engine
        if (analysisEngine == null) {
            analysisEngine = BuffaloAnalysisEngine(
                breedIdentifier = breedIdentifier,
                diseaseDetector = diseaseDetector,
                bodyAnalyzer = bodyAnalyzer,
                udderAnalyzer = udderAnalyzer
            )
        }

        // Utilities
        if (cameraManager == null) {
            cameraManager = CameraManager(context)
        }
        if (imageProcessor == null) {
            imageProcessor = ImageProcessor(context)
        }
        if (pdfGenerator == null) {
            pdfGenerator = PDFReportGenerator(context)
        }
        if (localizationManager == null) {
            localizationManager = LocalizationManager(context)
        }
        if (preferencesManager == null) {
            preferencesManager = PreferencesManager(context)
        }
    }

    fun getRepository(): BuffaloAnalysisRepository {
        return repository ?: throw IllegalStateException("Services not initialized")
    }

    fun getAnalysisEngine(): BuffaloAnalysisEngine {
        return analysisEngine ?: throw IllegalStateException("Services not initialized")
    }

    fun getCameraManager(): CameraManager {
        return cameraManager ?: throw IllegalStateException("Services not initialized")
    }

    fun getImageProcessor(): ImageProcessor {
        return imageProcessor ?: throw IllegalStateException("Services not initialized")
    }

    fun getPDFGenerator(): PDFReportGenerator {
        return pdfGenerator ?: throw IllegalStateException("Services not initialized")
    }

    fun getLocalizationManager(): LocalizationManager {
        return localizationManager ?: throw IllegalStateException("Services not initialized")
    }

    fun getPreferencesManager(): PreferencesManager {
        return preferencesManager ?: throw IllegalStateException("Services not initialized")
    }

    fun reset() {
        database = null
        repository = null
        analysisEngine = null
        cameraManager = null
        imageProcessor = null
        pdfGenerator = null
        localizationManager = null
        preferencesManager = null
    }
}
