package com.app.thingscrossing.feature_advertisement.domain.use_case

data class AdvertisementUseCases(
    val getAdvertisementList: GetAdvertisementListUseCase,
    val deleteAdvertisement: DeleteAdvertisementUseCase,
    val addAdvertisement: AddAdvertisementUseCase,
    val getAdvertisement: GetAdvertisementUseCase,
    val searchAdvertisements: SearchAdvertisementsUseCase,
)