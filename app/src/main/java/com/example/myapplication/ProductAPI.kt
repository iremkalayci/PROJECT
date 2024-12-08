package com.example.myapplication


import retrofit2.Response
import retrofit2.http.GET

interface  ProductAPI {

    @GET("iremkalayci/MyNotes/main/MyNotes.json")  // URL kısmı
    suspend fun getData(): Response<List<Product>>  // Görseli alacak fonksiyon
}

//raw.githubusercontent.com/iremkalayci/Prd/refs/heads/main/urunnn.json
//raw.githubusercontent.com/iremkalayci/urun/main/urunler.json
//raw.githubusercontent.com/iremkalayci/MyNotes/refs/heads/main/MyNotes.json