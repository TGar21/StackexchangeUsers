package com.example.stackexchange.data.model

data class TagItem(
    val answer_count: Int,
    val answer_score: Int,
    val question_count: Int,
    val question_score: Int,
    val tag_name: String,
    val user_id: Int
)