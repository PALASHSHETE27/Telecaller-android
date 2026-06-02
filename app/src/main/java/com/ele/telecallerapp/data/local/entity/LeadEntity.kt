package com.ele.telecallerapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "leads")
data class LeadEntity(
    @PrimaryKey val id: String,
    val name: String,
    val phone: String,
    val company: String?,
    val campaign: String?,
    val status: String,
    val source: String?,
    val priority: String?,
    val createdAt: String
)
