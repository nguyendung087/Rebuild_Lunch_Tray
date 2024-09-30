package com.example.rebuildlunchtray.ui

import androidx.lifecycle.ViewModel
import com.example.rebuildlunchtray.models.LunchUiState
import com.example.rebuildlunchtray.models.MenuItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.NumberFormat

class LunchViewModel : ViewModel() {
    private val tax = 0.08

    private var _uiState = MutableStateFlow(LunchUiState())
    val uiState : StateFlow<LunchUiState> = _uiState.asStateFlow()

    fun updateEntree(newEntreeItem: MenuItem.EntreeItem) {
        val previousEntreeItem = _uiState.value.entreeItem
        updateItem(newEntreeItem, previousEntreeItem)
    }

    fun updateSideDish(newSideDishItem : MenuItem.SideDishItem) {
        val previousSideDishItem = _uiState.value.sideDishItem
        updateItem(newSideDishItem, previousSideDishItem)
    }

    fun updateAccompaniment(newAccompanimentItem: MenuItem.AccompanimentItem) {
        val previousAccompanimentItem = _uiState.value.accompanimentItem
        updateItem(newAccompanimentItem, previousAccompanimentItem)
    }

    fun resetOrder() {
        _uiState.value = LunchUiState()
    }

    private fun updateItem(newItem : MenuItem, previousItem: MenuItem?) {
        val previousItems = previousItem?.price ?: 0.0

        val totalItemPrice = _uiState.value.itemTotalPrice - previousItems + newItem.price

        val currentTax = totalItemPrice * tax

        _uiState.update { currentState ->
            currentState.copy(
                itemTotalPrice = totalItemPrice,
                orderTax = currentTax,
                entreeItem = if (newItem is MenuItem.EntreeItem) newItem else currentState.entreeItem,
                sideDishItem = if (newItem is MenuItem.SideDishItem) newItem else currentState.sideDishItem,
                accompanimentItem = if (newItem is MenuItem.AccompanimentItem) newItem else currentState.accompanimentItem,
                orderTotalPrice = totalItemPrice + currentTax
            )
        }
    }
}

fun Double.priceFormat() : String {
    return NumberFormat.getCurrencyInstance().format(this)
}