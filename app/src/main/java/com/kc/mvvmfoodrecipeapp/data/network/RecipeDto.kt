package com.kc.mvvmfoodrecipeapp.data.network

import com.google.gson.annotations.SerializedName

data class RecipeDto(
    @SerializedName("pk")
    var pk: Int,

    @SerializedName("title")
    var title: String,

    @SerializedName("featured_image")
    var featured_image: String,

    @SerializedName("publisher")
    var publisher: String,

    @SerializedName("rating")
    var rating: Int,

    @SerializedName("source_url")
    var source_url: String,

    @SerializedName("description")
    var description: String,

    @SerializedName("cooking_instructions")
    var cooking_instructions: String,

    @SerializedName("ingredients")
    var ingredients: List<String>? = null,

    @SerializedName("date_added")
    var date_added: String,

    @SerializedName("date_updated")
    var date_updated: String
)
