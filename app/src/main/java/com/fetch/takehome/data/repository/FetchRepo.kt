package com.fetch.takehome.data.repository

import com.fetch.takehome.data.networking.FetchApi
import javax.inject.Inject

class FetchRepo @Inject constructor(private val fetchApi: FetchApi) {
    suspend fun fetchData() = fetchApi.getFetchData()
}
