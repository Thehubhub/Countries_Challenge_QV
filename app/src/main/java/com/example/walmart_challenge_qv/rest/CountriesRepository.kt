package com.example.walmart_challenge_qv.rest

import android.util.Log
import com.example.walmart_challenge_qv.utils.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

interface CountriesRepository {
    fun getAllCountries(): Flow<ResponseState>
}

class CountriesRepositoryImp: CountriesRepository{

    override fun getAllCountries(): Flow<ResponseState> =
        flow {
            emit(ResponseState.LOADING)
            try{
                val response = providesNetworkService().getAllCountries()
                if(response.isSuccessful){
                    response.body()?.let {
                        emit(ResponseState.SUCCESS(it))
                        Log.i("API SUCCESS", response.message())
                    }?: throw Exception("Response null")
                }else throw Exception("No response")
            }catch (e: Exception){
                emit(ResponseState.ERROR(e))
                e.message?.let { Log.i("API FAILS", it) }
            }
        }

    private fun providesNetworkService(): APIService {
        return Retrofit.Builder()
            .baseUrl(APIService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIService::class.java)
    }
}