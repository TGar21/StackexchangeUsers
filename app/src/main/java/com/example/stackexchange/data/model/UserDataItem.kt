package com.example.stackexchange.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDataItem(
    val badge_counts: BadgeCounts,
    val creation_date: Long,
    val display_name: String,
    val location: String,
    val profile_image: String,
    val reputation: Int,
    val user_id: Int
) : Parcelable