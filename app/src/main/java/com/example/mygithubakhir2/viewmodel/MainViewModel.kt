package com.example.mygithubakhir2.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.mygithubakhir2.ApiConfig
import com.example.mygithubakhir2.User
import com.example.mygithubakhir2.response.GithubResponse
import com.example.mygithubakhir2.response.SearchResponse
import com.example.mygithubakhir2.settings.SettingsPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val preferences: SettingsPreferences) : ViewModel() {
    init {
        setUser(USERNAME)
    }

    val _listUser = MutableLiveData<ArrayList<User>>()
    val listUser: LiveData<ArrayList<User>> = _listUser

    fun setUser(query: String) {
        val client = ApiConfig.getApiService().getUsers(USERNAME)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(call: Call<GithubResponse>, response: Response<GithubResponse>) {
                if (response.isSuccessful) {
                    _listUser.postValue(response.body()?.items)
                }
            }
            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                Log.d(ERROR, t.message.toString())
            }
        })
    }
    fun getUser(): LiveData<ArrayList<User>> = listUser

    fun getSearchUser(query: String) {
        val client = ApiConfig.getApiService().getSearchData(query)
        client.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                if (response.isSuccessful) {
                    val listUser = response.body()?.items
                    if (listUser != null) {
                        _listUser.postValue(listUser as ArrayList<User>?)
                    }
                }
            }
            override fun onFailure(call: retrofit2.Call<SearchResponse>, t: Throwable) {
                Log.e("MainViewModel", "onFailure: ${t.message}")
            }
        })
    }

    fun getTheme() = preferences.getTheme().asLiveData()

    class Factory(private val preferences: SettingsPreferences) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T = MainViewModel(preferences) as T
    }

    companion object {
        private const val ERROR = "Failure"
        private const val USERNAME = "q"
    }
}