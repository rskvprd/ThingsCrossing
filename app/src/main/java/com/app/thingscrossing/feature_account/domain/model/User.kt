package com.app.thingscrossing.feature_account.domain.model

data class User(
    val id: Int? = null,

    val username: String,
    val password: String?,
    val email: String? = null,

    val firstName: String? = null,
    val lastName: String? = null,

    val dateJoined: String? = null,
    val lastLogin: String? = null,

    val isActive: Boolean? = null,
    val isStaff: Boolean? = null,
    val isSuperuser: Boolean? = null,

    val groups: List<Any> = emptyList(), //TODO: Change Any to something else
    val userPermissions: List<Any> = emptyList() //TODO: Change Any to something else
)