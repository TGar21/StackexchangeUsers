package com.example.stackexchange.data.api

import com.example.stackexchange.data.model.UserData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

private const val QUERY = "/2.3/users?order=desc&sort=reputation&site=stackoverflow&filter=!0Z-LvhbSNwb7VsNYilJam(tyY"

interface ApiService {
    @GET(QUERY)
    fun getUsersByName(
        @Query("inname") name: String,
//        @Query("max") limit: Int
    ): Call<UserData>
}