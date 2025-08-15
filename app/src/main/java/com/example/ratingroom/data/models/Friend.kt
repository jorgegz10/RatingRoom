package com.example.ratingroom.data.models

data class Friend(
    val id: Int,
    val name: String,
    val username: String,
    val profileImageUrl: String? = null,
    val isOnline: Boolean = false,
    val lastSeen: String = "",
    val mutualFriends: Int = 0,
    val relationshipType: FriendshipType = FriendshipType.NONE,
    val favoriteGenres: List<String> = emptyList(),
    val totalReviews: Int = 0,
    val averageRating: Double = 0.0
)

enum class FriendshipType {
    FRIEND,
    FOLLOWING,
    FOLLOWER,
    MUTUAL,
    NONE
}

data class FriendRequest(
    val id: Int,
    val fromUserId: Int,
    val toUserId: Int,
    val status: RequestStatus,
    val timestamp: String
)

enum class RequestStatus {
    PENDING,
    ACCEPTED,
    REJECTED
}

data class FriendActivity(
    val friend: Friend,
    val action: String,
    val movie: String,
    val rating: Double,
    val comment: String,
    val timeAgo: String
)