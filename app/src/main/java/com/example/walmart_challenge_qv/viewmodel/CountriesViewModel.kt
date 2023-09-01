package com.example.walmart_challenge_qv.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.walmart_challenge_qv.rest.CountriesRepositoryImp
import com.example.walmart_challenge_qv.utils.ResponseState
import kotlinx.coroutines.launch

class CountriesViewModel : ViewModel(){

    //Retrieving list of countries through LiveData
    private val _countries: MutableLiveData<ResponseState> = MutableLiveData(ResponseState.LOADING)
    val countries: LiveData<ResponseState> get() = _countries

    private val countriesRepo= CountriesRepositoryImp()

    //Getting all countries
    fun getAllCountries(){
        viewModelScope.launch {
            countriesRepo.getAllCountries().collect{
                _countries.postValue(it)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("CountriesViewModel", "VIEWMODEL destroyed")
    }

}