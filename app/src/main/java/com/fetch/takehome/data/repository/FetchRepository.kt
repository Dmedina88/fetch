package com.fetch.takehome.data.repository

import com.fetch.takehome.data.networking.FetchApi
import com.fetch.takehome.data.networking.FetchData
import javax.inject.Inject


interface FetchRepository {
    suspend fun fetchData(): List<FetchData>
}

class FetchRepositoryImpl @Inject constructor(private val fetchApi: FetchApi) : FetchRepository {
    override suspend fun fetchData(): List<FetchData> = fetchApi.getFetchData()
}