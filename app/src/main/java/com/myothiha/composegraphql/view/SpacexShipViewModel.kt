package com.myothiha.composegraphql.view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.myothiha.composegraphql.domain.Ship
import com.myothiha.composegraphql.domain.SpacexShipsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Author Liam
 * Created at 01/Sep/2024
 */
@HiltViewModel
class SpacexShipViewModel @Inject constructor(
    private val repository: SpacexShipsRepository
) : ViewModel() {
    var ships by mutableStateOf(emptyFlow<PagingData<Ship>>())
        private set

    init {
        getShips()
    }

    private fun getShips() {
        viewModelScope.launch {
            ships = repository.getShips()
        }
    }
}