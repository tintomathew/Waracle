package com.tinto.waracletestapp.repository

import com.tinto.waracletestapp.model.CakeDataModel
import com.tinto.waracletestapp.service.CakesApiService
import com.tinto.waracletestapp.util.Resource
import retrofit2.Response
import javax.inject.Inject


class CakesRepository @Inject constructor(private val cakesApiService: CakesApiService) {
    suspend fun getSearchedImage(
    ): Resource<List<CakeDataModel>> {
        return responseToResource(cakesApiService.getSearchedImage())
    }

    private fun responseToResource(dataModel: Response<List<CakeDataModel>>): Resource<List<CakeDataModel>> {
        if (dataModel.isSuccessful) {
            dataModel.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(dataModel.message())
    }
}