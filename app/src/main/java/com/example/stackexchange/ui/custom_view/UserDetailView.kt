package com.example.stackexchange.ui.custom_view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.example.stackexchange.R
import com.example.stackexchange.data.model.BadgeCounts
import com.example.stackexchange.databinding.ViewUserDetailBinding
import com.example.stackexchange.util.DateUtils

@SuppressLint("SetTextI18n")
class UserDetailView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private var binding: ViewUserDetailBinding =
        ViewUserDetailBinding.inflate(LayoutInflater.from(context))

    init {
        addView(binding.root)
    }

    @SuppressLint("SetTextI18n")
    fun setAllTexts(
        newUserName: String?,
        newReputation: String?,
        newBadges: BadgeCounts?,
        newLocation: String?,
        newCreationDate: Long?
    ) {
        binding.apply {
            userName.text = "User name: $newUserName"
            reputation.text = "Reputation: $newReputation"
            badges.text = formatBadges(newBadges)
            location.text = "Location: $newLocation"
            creationDate.text =
                if (newCreationDate != null)
                    "Created:" + DateUtils().millisToDate(newCreationDate).toString()
                else ""
        }
    }

    fun setTags(tags: String) {
        binding.tags.apply {
            text = "Tags: $tags"
            visibility = VISIBLE
        }
    }

    fun loadImage(url: String) {
        Glide.with(this)
            .load(url) // image url
            .placeholder(R.drawable.ic_baseline_broken_image_24) // any placeholder to load at start
            .error(R.drawable.nothing)  // any image in case of error
            .override(200, 200) // resizing
            .centerCrop()
            .into(binding.avatar)
    }

    private fun formatBadges(badgeCounts: BadgeCounts?): String {
        if (badgeCounts == null) return "No badges"
        return "B: " + badgeCounts.bronze + ", S: " + badgeCounts?.silver + ", G: " + badgeCounts.gold
    }
}