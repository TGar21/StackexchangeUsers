package com.example.stackexchange.data.repository

import android.util.Log
import com.example.stackexchange.data.api.ApiFactory
import com.example.stackexchange.data.model.TagData
import com.example.stackexchange.ui.main.DetailViewModel
import com.example.stackexchange.util.State
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TagsModel {
    fun fetchTagsByUserId(id: Int, viewModel: DetailViewModel) {
        val retrofitData = ApiFactory().buildApiService().getTagsByID(id)
        viewModel.state.value = State.IN_PROGRESS

        retrofitData.enqueue(object : Callback<TagData> {
            override fun onResponse(
                call: Call<TagData>,
                response: Response<TagData>
            ) {
                val responseBody = response.body()
                Log.d("myTag", "response: $responseBody")
                viewModel._tagsLiveData.postValue(responseBody)
                viewModel.state.value = State.LOADED
            }

            override fun onFailure(call: Call<TagData>, t: Throwable) {
                viewModel.state.value = State.FAILED
                Log.d("myTag", "failure: ${t.localizedMessage}")
            }
        })
    }
}