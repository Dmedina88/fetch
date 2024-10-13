package com.fetch.takehome

import app.cash.turbine.test
import com.fetch.takehome.data.networking.FetchData
import com.fetch.takehome.data.repository.FetchRepository
import com.fetch.takehome.screens.FetchListViewModel
import com.fetch.takehome.screens.ListItems
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FetchViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun `test fetchData filters and sorts correctly`() = runTest {
        val mockRepo = mockk<FetchRepository>() // Create a mock of FetchRepository
        coEvery { mockRepo.fetchData() } returns mockFetchDataArray
        val viewModel = FetchListViewModel(mockRepo, testDispatcher)

        val expectedListItems = listOf(
            ListItems.ListHeader("1"),
            ListItems.ListItem(name = "Item 276", id = 276, listId = 1),
            ListItems.ListItem(name = "Item 684", id = 684, listId = 1),
            ListItems.ListHeader("2"),
            ListItems.ListItem(name = "Item 906", id = 906, listId = 2),
            ListItems.ListHeader("3"),
            ListItems.ListItem(name = "Item 680", id = 680, listId = 3),
            ListItems.ListHeader("4"),
            ListItems.ListItem(name = "Item 534", id = 534, listId = 4),
            ListItems.ListItem(name = "Item 88", id = 808, listId = 4)
        )


        viewModel.uiState.test {
            //test initial state
            assertFalse(awaitItem().isLoading)
            viewModel.fetchData()
            // test data loads correctly
            assertEquals(expectedListItems, awaitItem().listItems)

        }

    }

    @Test
    fun `test fetchData emits error state on exception`() = runTest {
        val mockRepo = mockk<FetchRepository>()
        val expectedException = Exception("Network error")
        coEvery { mockRepo.fetchData() } throws expectedException

        val viewModel = FetchListViewModel(mockRepo, testDispatcher)
        viewModel.fetchData()

        viewModel.uiState.test {
            viewModel.fetchData()
            assertEquals(expectedException.message, awaitItem().error) // Assert error state
            cancelAndIgnoreRemainingEvents()
        }
    }
}


private val mockFetchDataArray = listOf(
    FetchData(id = 203, listId = 2, name = ""),
    FetchData(id = 684, listId = 1, name = "Item 684"),
    FetchData(id = 276, listId = 1, name = "Item 276"),
    FetchData(id = 736, listId = 3, name = null),
    FetchData(id = 926, listId = 4, name = null),
    FetchData(id = 808, listId = 4, name = "Item 88"),
    FetchData(id = 599, listId = 1, name = null),
    FetchData(id = 424, listId = 2, name = null),
    FetchData(id = 444, listId = 1, name = ""),
    FetchData(id = 809, listId = 3, name = null),
    FetchData(id = 293, listId = 2, name = null),
    FetchData(id = 510, listId = 2, name = null),
    FetchData(id = 680, listId = 3, name = "Item 680"),
    FetchData(id = 231, listId = 2, name = null),
    FetchData(id = 534, listId = 4, name = "Item 534"),
    FetchData(id = 294, listId = 4, name = ""),
    FetchData(id = 439, listId = 1, name = null),
    FetchData(id = 156, listId = 2, name = null),
    FetchData(id = 906, listId = 2, name = "Item 906")
)