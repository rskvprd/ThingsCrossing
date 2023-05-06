package com.app.thingscrossing.feature_account.domain.use_case

import com.app.thingscrossing.core.Resource
import com.app.thingscrossing.feature_account.domain.model.Credentials
import com.app.thingscrossing.feature_account.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SignUpUseCase(private val accountRepository: AccountRepository) {
    /**
     * @return Auth token if registration is successful*/
    operator fun invoke(credentials: Credentials): Flow<Resource<String>> =
        Resource.handleResource {
            accountRepository.registerWithCredentials(credentials)
        }

}