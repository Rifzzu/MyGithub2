package com.example.mygithubakhir2.response

import com.example.mygithubakhir2.User
import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @field:SerializedName("total_count")
    val totalCount: Int,

    @field:SerializedName("incomplete_results")
    val incompleteResults: Boolean,

    @field:SerializedName("items")
    val items: List<User>
)
