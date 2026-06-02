package com.ele.telecallerapp.network

data class MessageTemplateDto(
    val _id: String,
    val title: String,
    val category: String,
    val content: String,
    val isNew: Boolean
)
