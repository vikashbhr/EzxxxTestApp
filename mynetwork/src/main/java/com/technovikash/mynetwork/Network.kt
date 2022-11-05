package com.technovikash.mynetwork

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface Network {

    @GET
    suspend fun fetchCustomUI(
        @Url url: String
    ): Response<CustomUiResponse>

    @GET
    suspend fun fetchImage(@Url url: String): Response<Any>
}