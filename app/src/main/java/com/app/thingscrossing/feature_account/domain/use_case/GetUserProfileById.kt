package com.app.thingscrossing.feature_account.domain.use_case

import com.app.thingscrossing.core.Resource
import com.app.thingscrossing.feature_account.domain.model.UserProfile
import com.app.thingscrossing.feature_account.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class GetUserProfileById(
    private val accountRepository: AccountRepository,
) {
    operator fun invoke(id: Int): Flow<Resource<UserProfile>> = flow {
        emit(Resource.Loading())
        emitAll(Resource.defaultHandleApiResource {
            accountRepository.getUserProfileById(id)
        })
    }
}