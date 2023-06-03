package com.app.thingscrossing.feature_advertisement.domain.use_case

import com.app.thingscrossing.R
import com.app.thingscrossing.core.Resource
import com.app.thingscrossing.feature_account.data.local.toAuthKey
import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement
import com.app.thingscrossing.feature_advertisement.domain.repository.AdvertisementRepository
import com.app.thingscrossing.services.AuthService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class GetMyAdvertisementList(
    private val authService: AuthService,
    private val repository: AdvertisementRepository,
) {
    operator fun invoke(): Flow<Resource<List<Advertisement>>> = flow {
        emit(Resource.Loading())
        while (!authService.isInitialized) {
            delay(10)
        }
        val authKey: String? = authService.authKey?.toAuthKey()
        if (authKey == null) {
            emit(Resource.Error(R.string.sign_in_before_check_your_advertisements))
            return@flow
        }

        emitAll(Resource.defaultHandleApiResource { repository.getMyAdvertisementList(authKey!!) })
    }
}