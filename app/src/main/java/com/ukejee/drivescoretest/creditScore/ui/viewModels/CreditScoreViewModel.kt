package com.ukejee.drivescoretest.creditScore.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ukejee.drivescoretest.creditScore.data.Status
import com.ukejee.drivescoretest.creditScore.data.repository.CreditScoreRepositoryContract
import com.ukejee.drivescoretest.creditScore.ui.presentation.CreditScoreUiState
import com.ukejee.drivescoretest.creditScore.ui.presentation.toCreditScoreState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreditScoreViewModel(
    private val repository: CreditScoreRepositoryContract
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreditScoreUiState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    fun getCreditScoreInformation() {
        viewModelScope.launch {
            repository.getCreditScoreInformation().collect { result ->
                when(result.status) {
                    Status.LOADING -> {
                        _uiState.update { oldState ->
                            oldState.copy(isLoading = true)
                        }
                    }
                    Status.SUCCESS -> {
                        result.data?.let {
                            _uiState.value = it.toCreditScoreState()
                        }
                    }
                    Status.ERROR -> {
                        _uiState.value = CreditScoreUiState(
                            error = result.message
                        )
                    }
                    else -> { }
                }
            }
        }
    }
}