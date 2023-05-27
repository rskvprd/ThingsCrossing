package com.app.thingscrossing.feature_account.domain.use_case

data class AccountUseCases (
    /** Get auth key from DataStore */
    val getAuthKeyUseCase: GetAuthKeyUseCase,
    val saveAuthKeyUseCase: SaveAuthKeyUseCase,
    /** Register new user and return SignUpResponse */
    val signUpUseCase: SignUpUseCase,
    val deleteAuthKeyUseCase: DeleteAuthKeyUseCase,
    /** Obtain auth token by given user and save this token to DataStore */
    val signInUseCase: SignInUseCase,
    val getCurrentUserProfileByTokenUseCase: GetCurrentUserProfileByTokenUseCase,
    val getUserProfileById: GetUserProfileById
)