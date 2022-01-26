package com.example.stackexchange.ui.main

import androidx.lifecycle.*
import com.example.stackexchange.data.model.UserData
import com.example.stackexchange.data.repository.Model
import com.example.stackexchange.util.State

class MainViewModel : ViewModel() {
    var _usersLiveData = MutableLiveData<UserData>()
    val usersLiveData: LiveData<UserData> = _usersLiveData
    var state = MutableLiveData<State>()

    fun fetchData(query:String){
        Model().fetchByName(query, this)
    }

    fun onSearchQuery(res: UserData) {
        _usersLiveData.postValue(res)
    }
}