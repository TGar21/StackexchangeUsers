package com.example.stackexchange.data.api

import com.example.stackexchange.data.model.TagData
import com.example.stackexchange.data.model.UserData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val BASE_URL = "https://api.stackexchange.com"
//private const val QUERY_USERS_BY_NAME = "/2.3/users?order=desc&sort=reputation&site=stackoverflow&filter=!0Z-LvhbSNwb7VsNYilJam(tyY"
private const val QUERY_USERS_BY_NAME = "/2.3/users?order=desc&sort=reputation&site=stackoverflow&filter=!u5X.fn4MeUASJUPV6-ASRyWq552RLOX"
private const val QUERY_TAGS_BY_USER = "/2.3/users/{id}/top-tags?site=stackoverflow"

interface ApiService {
    @GET(QUERY_USERS_BY_NAME)
    fun getUsersByName(
        @Query("inname") name: String,
        @Query("pagesize") limit: Int
    ): Call<UserData>

    @GET(QUERY_TAGS_BY_USER)
    fun getTagsByID(
        @Path("id") id: Int,
    ): Call<TagData>


}