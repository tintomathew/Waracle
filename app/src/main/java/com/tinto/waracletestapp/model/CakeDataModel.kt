package com.tinto.waracletestapp.model

import com.google.gson.annotations.SerializedName


data class CakeDataModel(@SerializedName("title") val title: String,
                         @SerializedName("desc") val desc: String,
                         @SerializedName("image") val image: String)
