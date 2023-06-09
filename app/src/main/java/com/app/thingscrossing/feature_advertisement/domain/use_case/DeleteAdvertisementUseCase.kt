package com.app.thingscrossing.feature_advertisement.domain.use_case

import com.app.thingscrossing.core.Resource
import com.app.thingscrossing.feature_account.data.local.toAuthKey
import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement
import com.app.thingscrossing.feature_advertisement.domain.repository.AdvertisementRepository
import com.app.thingscrossing.services.AuthService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class DeleteAdvertisementUseCase(
    private val repository: AdvertisementRepository,
    private val authService: AuthService,
) {
    operator fun invoke(advertisement: Advertisement): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading(progression = 0))
        while (!authService.isInitialized) {
            delay(10)
        }
        val authKey = authService.authKey ?: error("Deleting advertisement without authorization")
        emitAll(Resource.defaultHandleApiResource {
            repository.deleteAdvertisement(
                advertisement,
                authKey.toAuthKey()
            )
        })
    }
}