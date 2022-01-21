package com.example.stackexchange.api

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface InterfaceApi {
    @GET("/2.3/users?order=desc&sort=reputation&inname=Tadeas&site=stackoverflow&filter=!0Z-LvhbSNwb7VsNYilJam(tyY")
    fun getData(): Call<UserData>


    companion object {
        var BASE_URL = "https://api.stackexchange.com"

        fun create(): InterfaceApi {
            val retrofitBuilder = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
                .create(InterfaceApi::class.java)

            val retrofitData = retrofitBuilder.getData()

            retrofitData.enqueue(object : Callback<UserData> {
                override fun onResponse(
                    call: Call<UserData>,
                    response: Response<UserData>
                ) {
                    val responseBody = response.body()
                    Log.d("myTag", "response: $responseBody")
                }

                override fun onFailure(call: Call<UserData>, t: Throwable) {
                    Log.d("myTag", "failure: ${t.localizedMessage}")
                }
            })
            return retrofitBuilder
        }
    }
}