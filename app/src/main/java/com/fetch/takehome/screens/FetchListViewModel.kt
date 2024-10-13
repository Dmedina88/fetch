package com.fetch.takehome.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fetch.takehome.data.networking.FetchData
import com.fetch.takehome.data.repository.FetchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FetchListViewModel @Inject constructor(
    private val fetchRepo: FetchRepository,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {


    private val _uiState = MutableStateFlow(State())
    val uiState: StateFlow<State> = _uiState.asStateFlow()

    fun fetchData() {
        viewModelScope.launch(dispatcher) {
            try {
                _uiState.update { it.copy(isLoading = true, error = null) }
                val listItems = mutableListOf<ListItems>()
                var lastListId: Int? = null
                fetchRepo.fetchData()
                    .filter { !it.name.isNullOrBlank() }
                    .sortedWith(compareBy<FetchData> { it.listId }.thenBy { it.name })
                    .forEach { item ->
                        if (lastListId != item.listId) {
                            lastListId = item.listId
                            listItems.add(ListItems.ListHeader(item.listId.toString()))
                        }
                        listItems.add(
                            ListItems.ListItem(
                                name = item.name ?: "", //should never be null at this point
                                id = item.id,
                                listId = item.listId
                            )
                        )
                    }
                _uiState.update { it.copy(listItems = listItems, isLoading = false) }

            } catch (e: Throwable) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }

        }

    }

}

sealed class ListItems {
    data class ListItem(val name: String, val id: Int, val listId: Int) : ListItems()
    data class ListHeader(val header: String) : ListItems()
}

data class State(
    val listItems: List<ListItems> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)