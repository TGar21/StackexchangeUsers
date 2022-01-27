package com.example.stackexchange.ui.custom_view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.stackexchange.data.model.BadgeCounts
import com.example.stackexchange.databinding.ViewUserDetailBinding
import com.example.stackexchange.util.DateUtils

class UserDetailView(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private var binding: ViewUserDetailBinding =
        ViewUserDetailBinding.inflate(LayoutInflater.from(context))

    init {
        addView(binding.root)
    }

    fun setAllTexts(
        newUserName: String,
        newReputation: String,
        newTags: String,
        newBadges: BadgeCounts,
        newLocation: String,
        newCreationDate: Long
    ) {
        binding.apply {
            userName.text = newUserName
            reputation.text = newReputation
            tags.text = newTags
            badges.text = formatBadges(newBadges)
            location.text = newLocation
            creationDate.text = DateUtils().millisToDate(newCreationDate).toString()
        }
    }

    private fun formatBadges(badgeCounts: BadgeCounts): String =
        "B: " + badgeCounts.bronze + ", S: " + badgeCounts.silver + ", G: " + badgeCounts.gold
}