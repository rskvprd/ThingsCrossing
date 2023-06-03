package com.app.thingscrossing.feature_advertisement.domain.use_case

data class AdvertisementUseCases(
    val deleteAdvertisement: DeleteAdvertisementUseCase,
    val addAdvertisement: AddAdvertisementUseCase,
    val getAdvertisement: GetAdvertisementUseCase,
    val searchAdvertisements: SearchAdvertisementsUseCase,
    val uploadImageUseCase: UploadImageUseCase,
    val deleteImageUseCase: DeleteImageUseCase,
    val getMyAdvertisementList: GetMyAdvertisementList,
    val updateAdvertisement: UpdateAdvertisement,
)
