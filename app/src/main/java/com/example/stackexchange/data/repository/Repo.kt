package com.example.stackexchange.api

import android.util.Log
import com.example.stackexchange.main.MainViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Repo {
    var BASE_URL = "https://api.stackexchange.com"

    fun renameMethod(): InterfaceApi {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(InterfaceApi::class.java)

        val retrofitData = retrofitBuilder.getData("Petr")

        retrofitData.enqueue(object : Callback<UserData> {
            override fun onResponse(
                call: Call<UserData>,
                response: Response<UserData>
            ) {
                val responseBody = response.body()
                Log.d("myTag", "response: $responseBody")
                val viewModel = MainViewModel()

                viewModel._usersLiveData.postValue(responseBody?.items)
            }

            override fun onFailure(call: Call<UserData>, t: Throwable) {
                Log.d("myTag", "failure: ${t.localizedMessage}")
            }
        })
        return retrofitBuilder
    }

}