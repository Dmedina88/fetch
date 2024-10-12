package com.fetch.takehome.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fetch.takehome.R

@Composable
fun FetchListScreen(
    viewModel: FetchListViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    viewModel.fetchData()

    LazyColumn {

        items(uiState.listItems) { item ->
            when (item) {
                is ListItems.ListItem -> ListItem(item = item)
                is ListItems.ListHeader -> ListHeader(header = item)
            }
        }
        if (uiState.isLoading) {
            item {
                CircularProgressIndicator(modifier = Modifier.fillMaxWidth())
            }
        }
        uiState.error?.let {
            item {
                Text(text = it)
                Button(onClick = { viewModel.fetchData() }) {
                    Text(text = stringResource(R.string.retry))
                }
            }
        }
    }


}


@Composable
fun ListHeader(header: ListItems.ListHeader) {
    Text(
        text = stringResource(R.string.id_group, header.header),
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.onPrimary,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(top = 16.dp)
    )
    HorizontalDivider(
        color = MaterialTheme.colorScheme.onSecondary,
    )
}

@Composable
fun ListItem(item: ListItems.ListItem) {
    Text(
        text = stringResource(R.string.list_item_formate, item.name, item.id, item.listId),
        color = MaterialTheme.colorScheme.onSecondary,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.secondary)
            .padding(8.dp)
            .fillMaxWidth()
    )
    HorizontalDivider(
        color = MaterialTheme.colorScheme.onSecondary,
    )

}