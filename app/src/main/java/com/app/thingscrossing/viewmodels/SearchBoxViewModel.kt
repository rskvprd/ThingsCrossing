package com.app.thingscrossing.viewmodels

import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SearchBoxViewModel constructor() : ViewModel() {
    var searchValue by mutableStateOf("")
        private set

    var showEraseIcon by mutableStateOf(false)
        private set

    fun onSearchValueChanged(newSearchValue: String) {
        if (newSearchValue.isNotBlank()) {
            searchValue = newSearchValue
            showEraseIcon = true
        } else {
            searchValue = ""
            showEraseIcon = false
        }
    }

    fun onFilterClick() {

    }

    fun onSortClick() {

    }

    fun onEraseButtonClick() {
        searchValue = ""
        showEraseIcon = false
    }

    fun onSearch(scope: KeyboardActionScope) {

    }
}