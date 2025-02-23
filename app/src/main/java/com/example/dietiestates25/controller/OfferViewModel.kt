package com.example.dietiestates25.controller

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dietiestates25.model.Offer

class OfferViewModel: ViewModel() {
    private val _offerSent = MutableLiveData<Offer>()
    val selectedOffer: LiveData<Offer> get() = _offerSent

    fun selectOffer(offer: Offer) {
        _offerSent.value = offer
    }
}