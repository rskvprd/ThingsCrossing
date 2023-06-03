package com.app.thingscrossing.feature_advertisement.navigation

sealed interface AdvertisementScreen {
    val route: String

    class AddEdit(private val advertisementId: Int?) : AdvertisementScreen {
        override val route: String
            get() = if (advertisementId == null) DEFAULT_ROUTE else "$DEFAULT_ROUTE?$ID_KEY=$advertisementId"

        companion object {
            const val DEFAULT_ROUTE = "add-edit-advertisement-screen"
            const val ID_KEY: String = "advertisementId"
        }
    }

    class Detail(private val advertisementId: Int?) : AdvertisementScreen {
        override val route: String
            get() = if (advertisementId == null) DEFAULT_ROUTE else "$DEFAULT_ROUTE?$ID_KEY=$advertisementId"

        companion object {
            const val DEFAULT_ROUTE = "detail-advertisement-screen"
            const val ID_KEY = "advertisementId"
        }
    }

    object MyAdvertisements : AdvertisementScreen {
        override val route: String
            get() = "my-advertisements"
    }

    object Search : AdvertisementScreen {
        override val route: String
            get() = "search-screen"
    }

    companion object {
        const val ROUTE = "advertisements"
        val ALL = listOf(
            AddEdit(null),
            Detail,
            MyAdvertisements,
            Search,
        )
    }
}