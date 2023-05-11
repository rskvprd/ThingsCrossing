package com.app.thingscrossing.feature_account.domain.model

data class User(
    val id: Int? = null,

    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val password: String?,

    val dateJoined: String? = null,
    val lastLogin: String? = null,

    val isActive: Boolean? = null,
    val isStaff: Boolean? = null,
    val isSuperuser: Boolean? = null,

    val groups: List<Any> = emptyList(), //TODO
    val userPermissions: List<Any> = emptyList() //TODO
)