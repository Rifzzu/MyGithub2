package com.example.mygithubakhir2.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mygithubakhir2.ApiConfig
import com.example.mygithubakhir2.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel : ViewModel() {
    var _listFollowing = MutableLiveData<ArrayList<User>>()
    val listFollowing: LiveData<ArrayList<User>> = _listFollowing

    fun setFollowing(username : String) {
        val client = ApiConfig.getApiService().getFollowingUser(username)
        client.enqueue(object : Callback<ArrayList<User>> {
            override fun onResponse(
                call: Call<ArrayList<User>>,
                response: Response<ArrayList<User>>
            ) {
                if (response.isSuccessful) {
                    _listFollowing.postValue(response.body())
                }
            }
            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                Log.d("ERROR", t.message.toString())
            }
        })
    }
    fun getFollowing(): LiveData<ArrayList<User>> = listFollowing
}