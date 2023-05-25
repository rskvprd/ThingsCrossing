package com.app.thingscrossing.feature_advertisement.presentation.screen_search

import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement
import com.app.thingscrossing.feature_advertisement.domain.util.AdvertisementSortVariant

data class SearchState(
    val errorId: Int? = null,
    val isLoading: Boolean = false,
    val isOrderSectionVisible: Boolean = false,
    val isEraseIconVisible: Boolean = false,
    val isAscendingSort: Boolean = false,

    val searchValue: String = "",
    val currentBottomSheet: BottomSheet = BottomSheet.SortBottomSheet,
    val advertisements: List<Advertisement> = emptyList(),
    val sortVariant: AdvertisementSortVariant = AdvertisementSortVariant.Date,
)

sealed interface BottomSheet {
    object SortBottomSheet : BottomSheet
    object FilterBottomSheet : BottomSheet
}