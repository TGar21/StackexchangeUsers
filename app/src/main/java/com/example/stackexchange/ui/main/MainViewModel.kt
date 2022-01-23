package com.example.stackexchange.main

import androidx.lifecycle.*
import com.example.stackexchange.data.api.InterfaceApi
import com.example.stackexchange.data.model.UserDataItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * setValue: sets the value instantly.
 *
 *This is synchronous update where main thread calls setValue. With Kotlin’s property access syntax,
 *  you’ll often use value instead of setValue.
postValue: Asynchronous updating, means Observer doesn’t receive instant update, rather receives update
when UI thread is active. When you call postValue more than one time from background thread, LiveData
dispatches only the latest value to the downstream. Being asynchronous, this does not guarantee instant
update to the Observer.
 */


class MainViewModel : ViewModel() {
    var _usersLiveData: MutableLiveData<ArrayList<UserDataItem>>

    init {
        _usersLiveData = fetchUsers()
        _searchMoviesLiveData = Transformations.switchMap(_searchFieldTextLiveData) {
            fetchUsers(it)
        }
    }


    fun onSearchQuery(res:ArrayList<UserDataItem>){
        _usersLiveData.postValue(res)
    }

    private fun fetchUsers(query: String = ""): LiveData<ArrayList<UserDataItem>> {
        val liveData = MutableLiveData<ArrayList<UserDataItem>>()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                //1
//                withContext(Dispatchers.Main) {
//                    movieLoadingStateLiveData.value = MovieLoadingState.LOADING
//                }

                val movies = InterfaceApi.fetchMovieByQuery(query)
                liveData.postValue(movies)

                //2
//                movieLoadingStateLiveData.postValue(MovieLoadingState.LOADED)
            } catch (e: Exception) {
                //3
//                movieLoadingStateLiveData.postValue(MovieLoadingState.INVALID_API_KEY)
            }
        }
        return liveData
    }

}