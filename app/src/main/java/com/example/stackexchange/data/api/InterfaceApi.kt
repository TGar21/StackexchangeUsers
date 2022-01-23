package com.example.stackexchange.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

private const val QUERY = "/2.3/users?order=desc&sort=reputation&site=stackoverflow&filter=!0Z-LvhbSNwb7VsNYilJam(tyY"

interface InterfaceApi {
    @GET(QUERY)
    fun getData(@Query("inname") name:String): Call<UserData>
}