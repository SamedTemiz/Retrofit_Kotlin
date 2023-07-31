package com.samedtemiz.retrofit_kotlin.model

import com.google.gson.annotations.SerializedName

//Bu tür sınıflarda çok fazla metot bulunmaz, sadece veri işlemek için kullanılır.
//Constructor yazmak zorunludur


data class CryptoModel(val currency : String, val price : String)
