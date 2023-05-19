package com.app.thingscrossing.feature_account.domain.model

data class UserProfile(
    val id: Int,
    val avatar: String,
    val user: User
) {
    companion object {
        val DEFAULT_ME = UserProfile(
            id = 1,
            avatar = "",
            user = User(
                id = 1,
                username = "asdf",
                password = null,
                email = "dsafdas@sadf.ru",
                firstName = "Vasiliy",
                lastName = "Vyazkov",
                dateJoined = "2023.05.01",
                lastLogin = "2023.05.01T15:25:25Z",
                isActive = true,
            )
        )

        val DEFAULT_COMPANION = UserProfile(
            id = 2,
            avatar = "",
            user = User(
                id = 2,
                username = "kjhkjlh",
                password = null,
                email = "lkjlkj@sadf.ru",
                firstName = "Oleg",
                lastName = "Olegov",
                dateJoined = "2021.05.01",
                lastLogin = "2021.05.01T15:25:25Z",
                isActive = true,
            )
        )
    }
}