package com.example.ratingroom.data.models

import androidx.annotation.DrawableRes

data class FriendActivity(
    val id: Int,
    val friend: Friend,
    val movie: String,
    val action: String, // "rated", "reviewed", "added_to_list", etc.
    val rating: Int? = null,
    val comment: String? = null,
    val timestamp: String,
    @DrawableRes val posterRes: Int

)