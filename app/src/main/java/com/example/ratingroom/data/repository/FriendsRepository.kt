package com.example.ratingroom.data.repository

import com.example.ratingroom.data.models.*

object FriendsRepository {
    
    private val friends = mutableListOf(
        Friend(
            id = 1,
            name = "Carlos Mendoza",
            username = "@carlos_m",
            isOnline = true,
            mutualFriends = 3,
            relationshipType = FriendshipType.FRIEND,
            favoriteGenres = listOf("Sci-Fi", "Action"),
            totalReviews = 45,
            averageRating = 4.2
        ),
        Friend(
            id = 2,
            name = "María García",
            username = "@maria_garcia",
            isOnline = false,
            lastSeen = "Hace 2 horas",
            mutualFriends = 7,
            relationshipType = FriendshipType.FRIEND,
            favoriteGenres = listOf("Romance", "Drama"),
            totalReviews = 32,
            averageRating = 4.5
        ),
        Friend(
            id = 3,
            name = "Luis Rodríguez",
            username = "@luis_rod",
            isOnline = false,
            lastSeen = "Hace 1 día",
            mutualFriends = 2,
            relationshipType = FriendshipType.FOLLOWING,
            favoriteGenres = listOf("Crime", "Thriller"),
            totalReviews = 28,
            averageRating = 4.1
        ),
        Friend(
            id = 4,
            name = "Ana Martín",
            username = "@ana_martin",
            isOnline = true,
            mutualFriends = 5,
            relationshipType = FriendshipType.FOLLOWER,
            favoriteGenres = listOf("Comedy", "Adventure"),
            totalReviews = 67,
            averageRating = 4.7
        ),
        Friend(
            id = 5,
            name = "Pedro Sánchez",
            username = "@pedro_s",
            isOnline = false,
            lastSeen = "Hace 3 días",
            mutualFriends = 1,
            relationshipType = FriendshipType.MUTUAL,
            favoriteGenres = listOf("Horror", "Sci-Fi"),
            totalReviews = 19,
            averageRating = 3.8
        )
    )
    
    private val friendRequests = mutableListOf(
        FriendRequest(
            id = 1,
            fromUserId = 6,
            toUserId = 1,
            status = RequestStatus.PENDING,
            timestamp = "2024-01-20"
        )
    )
    
    fun getAllFriends(): List<Friend> = friends.filter { it.relationshipType == FriendshipType.FRIEND || it.relationshipType == FriendshipType.MUTUAL }
    
    fun getFollowing(): List<Friend> = friends.filter { it.relationshipType == FriendshipType.FOLLOWING || it.relationshipType == FriendshipType.MUTUAL }
    
    fun getFollowers(): List<Friend> = friends.filter { it.relationshipType == FriendshipType.FOLLOWER || it.relationshipType == FriendshipType.MUTUAL }
    
    fun searchFriends(query: String): List<Friend> {
        return friends.filter { 
            it.name.contains(query, ignoreCase = true) ||
            it.username.contains(query, ignoreCase = true)
        }
    }
    
    fun addFriend(friendId: Int) {
        val index = friends.indexOfFirst { it.id == friendId }
        if (index != -1) {
            friends[index] = friends[index].copy(relationshipType = FriendshipType.FRIEND)
        }
    }
    
    fun removeFriend(friendId: Int) {
        val index = friends.indexOfFirst { it.id == friendId }
        if (index != -1) {
            friends[index] = friends[index].copy(relationshipType = FriendshipType.NONE)
        }
    }
    
    fun followUser(friendId: Int) {
        val index = friends.indexOfFirst { it.id == friendId }
        if (index != -1) {
            friends[index] = friends[index].copy(relationshipType = FriendshipType.FOLLOWING)
        }
    }
    
    fun unfollowUser(friendId: Int) {
        val index = friends.indexOfFirst { it.id == friendId }
        if (index != -1) {
            friends[index] = friends[index].copy(relationshipType = FriendshipType.NONE)
        }
    }
    
    fun getPendingRequests(): List<FriendRequest> = friendRequests.filter { it.status == RequestStatus.PENDING }
    
    fun getFriendsActivity(): List<FriendActivity> {
        return listOf(
            FriendActivity(
                friend = friends.first { it.id == 1 },
                action = "Reseña",
                movie = "Inception (2010)",
                rating = 5.0,
                comment = "Una obra maestra del cine. La cinematografía y la banda sonora son increíbles.",
                timeAgo = "Hace 2 horas"
            ),
            FriendActivity(
                friend = friends.first { it.id == 2 },
                action = "Calificación",
                movie = "The Matrix (1999)",
                rating = 4.0,
                comment = "",
                timeAgo = "Hace 4 horas"
            ),
            FriendActivity(
                friend = friends.first { it.id == 3 },
                action = "Lista",
                movie = "Dune (2021)",
                rating = 0.0,
                comment = "Agregó a su lista para ver después",
                timeAgo = "Hace 6 horas"
            )
        )
    }
}