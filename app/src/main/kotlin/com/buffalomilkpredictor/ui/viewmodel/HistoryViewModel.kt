package com.buffalomilkpredictor.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buffalomilkpredictor.data.model.BuffaloAnalysis
import com.buffalomilkpredictor.data.repository.BuffaloAnalysisRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

sealed class HistoryState {
    object Loading : HistoryState()
    data class Success(val analyses: List<BuffaloAnalysis>) : HistoryState()
    data class Error(val message: String) : HistoryState()
    object Empty : HistoryState()
}

class HistoryViewModel(private val repository: BuffaloAnalysisRepository) : ViewModel() {

    private val _historyState = MutableStateFlow<HistoryState>(HistoryState.Loading)
    val historyState: StateFlow<HistoryState> = _historyState.asStateFlow()

    init {
        loadHistory()
    }

    fun loadHistory() {
        viewModelScope.launch {
            try {
                _historyState.value = HistoryState.Loading
                
                repository.getAllAnalysisFlow().collect { analyses ->
                    if (analyses.isEmpty()) {
                        _historyState.value = HistoryState.Empty
                    } else {
                        _historyState.value = HistoryState.Success(analyses)
                    }
                }
                
                Timber.d("History loaded successfully")
            } catch (e: Exception) {
                Timber.e(e, "Error loading history")
                _historyState.value = HistoryState.Error("Failed to load history: ${e.message}")
            }
        }
    }

    fun deleteAnalysis(analysis: BuffaloAnalysis) {
        viewModelScope.launch {
            try {
                repository.deleteAnalysis(analysis)
                Timber.d("Analysis deleted: ${analysis.id}")
                loadHistory()
            } catch (e: Exception) {
                Timber.e(e, "Error deleting analysis")
            }
        }
    }

    fun searchByBreed(breed: String) {
        viewModelScope.launch {
            try {
                repository.getAnalysisByBreed(breed).collect { analyses ->
                    if (analyses.isEmpty()) {
                        _historyState.value = HistoryState.Empty
                    } else {
                        _historyState.value = HistoryState.Success(analyses)
                    }
                }
            } catch (e: Exception) {
                Timber.e(e, "Error searching by breed")
                _historyState.value = HistoryState.Error("Search failed: ${e.message}")
            }
        }
    }
}
