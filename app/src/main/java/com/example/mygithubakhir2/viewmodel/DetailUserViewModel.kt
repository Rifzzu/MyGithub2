package com.example.mygithubakhir2.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mygithubakhir2.ApiConfig
import com.example.mygithubakhir2.favorite.FavoriteUser
import com.example.mygithubakhir2.favorite.FavoriteUserDao
import com.example.mygithubakhir2.favorite.UserDatabase
import com.example.mygithubakhir2.response.DetailUserResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : AndroidViewModel(application) {
    val _user = MutableLiveData<DetailUserResponse>()
    val user: LiveData<DetailUserResponse> = _user

    private var userDao: FavoriteUserDao?
    private var userDatabase: UserDatabase?

    init {
        userDatabase = UserDatabase.getInstance(application)
        userDao = userDatabase?.favoriteUserDao()
    }

    fun setDetailUser(username: String) {
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                if (response.isSuccessful) {
                    _user.postValue(response.body())
                }
            }
            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                Toast.makeText(getApplication(), t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getDetailUser(): LiveData<DetailUserResponse> = user

    fun insertFavoriteUser(username: String, id: Int, avatarUrl: String) {
        CoroutineScope(Dispatchers.IO).launch {
            var user = FavoriteUser(username, id, avatarUrl)
            userDao?.insertFavoriteUser(user)
        }
    }

    suspend fun isFavorite(id: Int) = userDao?.isFavorite(id)

    fun deleteFavoriteUser(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.deleteFavoriteUser(id)
        }
    }
}