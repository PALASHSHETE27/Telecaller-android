package com.ele.telecallerapp.data.repository

import com.ele.telecallerapp.network.MessageTemplateDto
import com.ele.telecallerapp.network.RetrofitClient

class MessageTemplateRepository {

    suspend fun getTemplates(): List<MessageTemplateDto> =
        RetrofitClient.api.getMessageTemplates()

    suspend fun updateTemplate(template: MessageTemplateDto): MessageTemplateDto =
        RetrofitClient.api.updateMessageTemplate(template._id, template)
}
