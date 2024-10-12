package com.fetch.takehome.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fetch.takehome.data.networking.FetchApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FetchListViewModel @Inject constructor(
    private val fetchApi: FetchApi
) : ViewModel() {

    fun fetchData() {
        viewModelScope.launch {
            val data = fetchApi.getHiringData()
            Log.d("FetchListViewModel", "Data: $data")
        }

    }

}