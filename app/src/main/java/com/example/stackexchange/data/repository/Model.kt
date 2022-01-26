package com.example.stackexchange.data.repository

import android.util.Log
import com.example.stackexchange.data.api.ApiService
import com.example.stackexchange.data.model.UserData
import com.example.stackexchange.ui.main.MainViewModel
import com.example.stackexchange.util.State
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val LIMIT = 2

class Model {
    private var BASE_URL = "https://api.stackexchange.com"

    private fun getApiService(): ApiService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiService::class.java)
    }

    fun fetchByName(query: String, viewModel: MainViewModel) {
        val retrofitData = getApiService().getUsersByName(query)
        viewModel.state.value = State.IN_PROGRESS

        retrofitData.enqueue(object : Callback<UserData> {
            override fun onResponse(
                call: Call<UserData>,
                response: Response<UserData>
            ) {
                val responseBody = response.body()
                Log.d("myTag", "response: $responseBody")
                viewModel._usersLiveData.postValue(responseBody)
                viewModel.state.value = State.LOADED
            }

            override fun onFailure(call: Call<UserData>, t: Throwable) {
                viewModel.state.value = State.FAILED
                Log.d("myTag", "failure: ${t.localizedMessage}")
            }
        })
    }

}