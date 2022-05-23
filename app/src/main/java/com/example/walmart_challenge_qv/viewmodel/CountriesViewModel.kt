package com.example.walmart_challenge_qv.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.walmart_challenge_qv.model.CountriesDetails
import com.example.walmart_challenge_qv.rest.CountriesRepository
import com.example.walmart_challenge_qv.utils.BaseViewModel
import com.example.walmart_challenge_qv.utils.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountriesViewModel @Inject constructor(
    private val countriesRepo: CountriesRepository,
    private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel(){

    //Retrieving list of countries through LiveData
    private val _countries: MutableLiveData<ResponseState> = MutableLiveData(ResponseState.LOADING)
    val countries: LiveData<ResponseState> get() = _countries

    var countryDetails: CountriesDetails? = null

    //Getting all countries
    fun getAllCountries(){
        viewModelSafeScope.launch(ioDispatcher) {
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