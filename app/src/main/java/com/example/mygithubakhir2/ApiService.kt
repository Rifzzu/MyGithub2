package com.example.mygithubakhir2

import com.example.mygithubakhir2.response.DetailUserResponse
import com.example.mygithubakhir2.response.GithubResponse
import com.example.mygithubakhir2.response.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ) : Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowerUser(
        @Path("username") username: String
    ) : Call<ArrayList<User>>

    @GET("users/{username}/following")
    fun getFollowingUser(
        @Path("username") username: String
    ) : Call<ArrayList<User>>

    @GET("search/users")
    fun getSearchData(
        @Query("q") query: String
    ) : Call<SearchResponse>

    @GET("search/users")
    fun getUsers(
        @Query("q") query: String
    ) : Call<GithubResponse>
}