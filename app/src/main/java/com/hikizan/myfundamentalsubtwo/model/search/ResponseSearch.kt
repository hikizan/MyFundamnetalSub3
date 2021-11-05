package com.hikizan.myfundamentalsubtwo.model.search


import com.google.gson.annotations.SerializedName

data class ResponseSearch(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    @SerializedName("items")
    val items: List<Item>,
    @SerializedName("total_count")
    val totalCount: Int
)