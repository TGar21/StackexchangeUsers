package com.example.stackexchange.data.model

data class TagData(
    val has_more: Boolean,
    val items: List<TagItem>,
    val quota_max: Int,
    val quota_remaining: Int
)