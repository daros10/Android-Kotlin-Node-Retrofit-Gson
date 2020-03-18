package com.example.globofly.Interface

import com.example.globofly.Model.User
import retrofit2.Call
import retrofit2.http.GET

interface JsonPLaceHolderApi {

    @GET("posts")
    fun getUserInformation() : Call<List<User>>

}