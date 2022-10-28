package com.tinto.waracletestapp.listeners

import com.tinto.waracletestapp.model.CakeDataModel

// click listener for cake item click */
interface CakeItemClick {
    fun onCakeClick(cakeDataModel: CakeDataModel)
}