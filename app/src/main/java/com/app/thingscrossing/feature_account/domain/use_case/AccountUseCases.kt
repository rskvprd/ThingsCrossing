package com.app.thingscrossing.feature_account.domain.use_case

data class AccountUseCases (
    val getAuthKeyUseCase: GetAuthKeyUseCase,
    val saveAuthKeyUseCase: SaveAuthKeyUseCase,
    val signUpUseCase: SignUpUseCase,
    val deleteAuthKeyUseCase: DeleteAuthKeyUseCase,
    val signInUseCase: SignInUseCase,
    val getCurrentUserProfileByTokenUseCase: GetCurrentUserProfileByTokenUseCase,
)