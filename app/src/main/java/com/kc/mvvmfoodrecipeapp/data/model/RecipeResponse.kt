package com.kc.mvvmfoodrecipeapp.data.model

import com.google.gson.annotations.SerializedName
import com.kc.mvvmfoodrecipeapp.data.network.RecipeDto

data class RecipeResponse(
    @SerializedName("count")
    var count: Int,

    @SerializedName("results")
    var recipes: List<RecipeDto>
)
