package com.app.thingscrossing.feature_advertisement.domain.use_case

data class SearchUseCases(
    val getAdvertisementList: GetAdvertisementListUseCase,
    val deleteAdvertisement: DeleteAdvertisementUseCase,
    val addAdvertisement: AddAdvertisementUseCase,
    val getAdvertisement: GetAdvertisementUseCase,
)
