package com.example.mygithubakhir2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.mygithubakhir2.favorite.FavoriteUser
import com.example.mygithubakhir2.favorite.FavoriteUserDao
import com.example.mygithubakhir2.favorite.UserDatabase

class FavoriteViewModel(application: Application): AndroidViewModel(application) {
    private var userDao: FavoriteUserDao?
    private var userDatabase: UserDatabase?

    init {
        userDatabase = UserDatabase.getInstance(application)
        userDao = userDatabase?.favoriteUserDao()
    }

    fun getFavoriteUser(): LiveData<List<FavoriteUser>>? {
        return userDao?.getFavoriteUser()
    }
}