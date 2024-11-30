package com.kaeritei.githubclient.data.api.payload

data class UserSearchPayload(
    val query: String,
    val target: SearchTargetType,
) {
    sealed interface SearchTargetType {
        val typeString: String

        data object User : SearchTargetType {
            override val typeString: String
                get() = "user"
        }

        data object Organization : SearchTargetType {
            override val typeString: String
                get() = "organization"
        }
    }
}
