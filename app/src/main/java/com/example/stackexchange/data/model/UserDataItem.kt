package com.example.stackexchange.data.model

data class UserDataItem(
    val badge_counts: BadgeCounts,
    val creation_date: Int,
    val display_name: String,
    val location: String,
    val profile_image: String,
    val reputation: Int,
    val user_id: Int
)