package com.example.ratingroom.ui.screens.friends

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ratingroom.data.repository.FriendsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FriendsViewModel : ViewModel() {
    
    private val _uiState = MutableStateFlow(FriendsUIState())
    val uiState: StateFlow<FriendsUIState> = _uiState.asStateFlow()
    
    init {
        loadFriendsData()
    }
    
    private fun loadFriendsData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            try {
                val friends = FriendsRepository.getFriends()
                val suggestions = FriendsRepository.getSuggestions()
                val followers = FriendsRepository.getFollowers()
                
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    friends = friends,
                    suggestions = suggestions,
                    followers = followers
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message
                )
            }
        }
    }
    
    fun onSearchQueryChange(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
        // Implementar búsqueda
    }
    
    fun onTabSelected(tab: Int) {
        _uiState.value = _uiState.value.copy(selectedTab = tab)
    }
    
    fun onFriendAction(friendId: Int, action: String) {
        viewModelScope.launch {
            try {
                when (action) {
                    "add_friend" -> FriendsRepository.addFriend(friendId)
                    "remove_friend" -> FriendsRepository.removeFriend(friendId)
                    "follow" -> FriendsRepository.followUser(friendId)
                    "unfollow" -> FriendsRepository.unfollowUser(friendId)
                }
                loadFriendsData() // Recargar datos
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            }
        }
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}