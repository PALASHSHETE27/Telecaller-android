
package com.ele.telecallerapp.network

data class ProfileResponse(
    val success: Boolean,
    val user: UserProfile
)

data class UpdateProfileResponse(
    val success: Boolean,
    val message: String,
    val user: UserProfile?
)

data class UserProfile(
    val _id: String,
    val name: String,
    val email: String,
    val mobile: String?,
    val employeeId: String?,
    val profileImage: String?
)

