package com.tinto.waracletestapp.service

import com.tinto.waracletestapp.model.CakeDataModel
import retrofit2.Response
import retrofit2.http.GET


interface CakesApiService {
    @GET("waracle_cake-android-client")
    suspend fun getSearchedImage(): Response<List<CakeDataModel>>
}