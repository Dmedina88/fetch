package com.fetch.takehome.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fetch.takehome.R


@Composable
fun FetchListScreen(
    viewModel: FetchListViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    viewModel.fetchData()

    FetchListContent(
        listItems = uiState.listItems,
        isLoading = uiState.isLoading,
        error = uiState.error,
        onRetryClick = { viewModel.fetchData() }
    )
}

@Composable
fun FetchListContent(
    listItems: List<ListItems>,
    isLoading: Boolean,
    error: String? = null,
    onRetryClick: () -> Unit = {}
) {
    LazyColumn {
        items(listItems) { item ->
            when (item) {
                is ListItems.ListItem -> ListItem(item = item)
                is ListItems.ListHeader -> ListHeader(header = item)
            }
        }
        if (isLoading) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
        error?.let {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = it)
                    Button(onClick = onRetryClick) {
                        Text(text = stringResource(R.string.retry))
                    }
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
            .padding(16.dp)
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


@Preview(showBackground = true)
@Composable
fun FetchListContentPreview() {
    FetchListContent(
        listItems = listOf(
            ListItems.ListHeader(header = "1"),
            ListItems.ListItem(id = 1, listId = 1, name = "Item 1")
        ),
        isLoading = false
    )
}

@Preview(showBackground = true)
@Composable
fun FetchListContentPreviewLoading() {
    FetchListContent(
        listItems = listOf(
            ListItems.ListHeader(header = "1"),
            ListItems.ListItem(id = 1, listId = 1, name = "Item 1")
        ),
        isLoading = true
    )
}

@Preview(showBackground = true)
@Composable
fun FetchListContentPreviewError() {
    FetchListContent(
        listItems = emptyList(),
        isLoading = false,
        error = "Error"
    )
}
