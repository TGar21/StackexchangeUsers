package com.example.stackexchange.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.stackexchange.data.model.TagData
import com.example.stackexchange.data.repository.TagsModel
import com.example.stackexchange.util.State

class DetailViewModel {

    val _tagsLiveData = MutableLiveData<TagData>()
    val tagsLiveData: LiveData<TagData> = _tagsLiveData
    var state = MutableLiveData<State>()

    fun fetchTags(id:Int){
        TagsModel().fetchTagsByUserId(id, this)
    }
}
