package com.tinto.waracletestapp.cakes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tinto.waracletestapp.model.CakeDataModel
import com.tinto.waracletestapp.repository.CakesRepository
import com.tinto.waracletestapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CakesViewModel @Inject constructor(
    private val cakesRepository: CakesRepository
) :
    ViewModel() {
    var response: MutableLiveData<List<CakeDataModel>> = MutableLiveData()
    var isLoading: MutableLiveData<Boolean> = MutableLiveData()
    var networkError: MutableLiveData<String> = MutableLiveData()

    init {
        isLoading.postValue(true)
    }

    /* initiating network call to pull the cakes data */
    fun getCakesData() {
        viewModelScope.launch(Dispatchers.IO) {
            val responseData = cakesRepository.getSearchedImage()
            if (responseData is Resource.Success) {
                responseData.data?.distinct()?.sortedBy { it.title }?.let {
                        response.postValue(it)
                        isLoading.postValue(false)
                    }
            } else {
                isLoading.postValue(false)
                networkError.postValue("Error in fetching data")
            }

        }
    }
}
