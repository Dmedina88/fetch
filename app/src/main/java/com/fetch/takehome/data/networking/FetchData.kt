package com.fetch.takehome.data.networking


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FetchData(
    @SerialName("id")
    val id: Int,
    @SerialName("listId")
    val listId: Int,
    @SerialName("name")
    val name: String?
)