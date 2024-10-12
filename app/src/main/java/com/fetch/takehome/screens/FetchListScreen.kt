package com.fetch.takehome.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun FetchListScreen(
     viewModel: FetchListViewModel = hiltViewModel()
) {

    Column {
        Button({ viewModel.fetchData()}) {
           Text("Fetch Data")
        }
    }

}