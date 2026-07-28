package com.buffalomilkpredictor.ui.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buffalomilkpredictor.data.model.BuffaloAnalysis
import com.buffalomilkpredictor.data.repository.BuffaloAnalysisRepository
import com.buffalomilkpredictor.ml.analysis.BuffaloAnalysisEngine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

sealed class AnalysisState {
    object Idle : AnalysisState()
    object Loading : AnalysisState()
    data class Success(val analysis: BuffaloAnalysis) : AnalysisState()
    data class Error(val message: String) : AnalysisState()
}

class AnalysisViewModel(
    private val analysisEngine: BuffaloAnalysisEngine,
    private val repository: BuffaloAnalysisRepository
) : ViewModel() {

    private val _analysisState = MutableStateFlow<AnalysisState>(AnalysisState.Idle)
    val analysisState: StateFlow<AnalysisState> = _analysisState.asStateFlow()

    private val _selectedImages = MutableStateFlow<Map<String, Bitmap>>(emptyMap())
    val selectedImages: StateFlow<Map<String, Bitmap>> = _selectedImages.asStateFlow()

    fun addImage(label: String, bitmap: Bitmap) {
        val updated = _selectedImages.value.toMutableMap()
        updated[label] = bitmap
        _selectedImages.value = updated
        Timber.d("Added image: $label, total: ${updated.size}")
    }

    fun removeImage(label: String) {
        val updated = _selectedImages.value.toMutableMap()
        updated.remove(label)
        _selectedImages.value = updated
        Timber.d("Removed image: $label, total: ${updated.size}")
    }

    fun clearImages() {
        _selectedImages.value = emptyMap()
        Timber.d("All images cleared")
    }

    fun startAnalysis(notes: String = "") {
        if (_selectedImages.value.isEmpty()) {
            _analysisState.value = AnalysisState.Error("Please select at least one image")
            return
        }

        viewModelScope.launch {
            try {
                _analysisState.value = AnalysisState.Loading
                Timber.d("Starting analysis with ${_selectedImages.value.size} images")

                val analysis = analysisEngine.analyzeBuffaloImage(
                    images = _selectedImages.value,
                    notes = notes
                )

                // Save to database
                val id = repository.insertAnalysis(analysis)
                Timber.d("Analysis saved with ID: $id")

                _analysisState.value = AnalysisState.Success(analysis)
            } catch (e: Exception) {
                Timber.e(e, "Error during analysis")
                _analysisState.value = AnalysisState.Error("Analysis failed: ${e.message}")
            }
        }
    }

    fun resetState() {
        _analysisState.value = AnalysisState.Idle
    }
}
