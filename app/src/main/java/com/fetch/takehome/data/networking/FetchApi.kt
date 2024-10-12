package com.fetch.takehome.data.networking

import retrofit2.http.GET

interface FetchApi {
    @GET("hiring.json")
    suspend fun getHiringData(): List<FetchData>
}