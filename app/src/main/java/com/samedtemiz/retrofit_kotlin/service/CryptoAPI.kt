package com.samedtemiz.retrofit_kotlin.service

import com.samedtemiz.retrofit_kotlin.model.CryptoModel
import io.reactivex.Observable
import io.reactivex.Observer
import retrofit2.Call
import retrofit2.http.GET

interface CryptoAPI {
    //GET, POST, DELETE, UPDATE
    //https://raw.githubusercontent.com/atilsamancioglu/K21-JSONDataSet/master/crypto.json

    @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json")
    fun getData(): Observable<List<CryptoModel>>
//    fun getData(): Call<List<CryptoModel>>

}