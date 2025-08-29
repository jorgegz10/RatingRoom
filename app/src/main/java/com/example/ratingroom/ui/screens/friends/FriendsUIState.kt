package com.example.ratingroom.ui.screens.friends

import com.example.ratingroom.data.models.Friend

data class FriendsUIState(
    val searchQuery: String = "",
    val selectedTab: Int = 0,
    val friends: List<Friend> = emptyList(),
    val suggestions: List<Friend> = emptyList(),
    val followers: List<Friend> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)