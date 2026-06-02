package com.ele.telecallerapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ele.telecallerapp.data.repository.MessageTemplateRepository
import com.ele.telecallerapp.network.MessageTemplateDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MessageTemplateViewModel : ViewModel() {

    private val repo = MessageTemplateRepository()

    private val _templates = MutableStateFlow<List<MessageTemplateDto>>(emptyList())
    val templates = _templates.asStateFlow()

    init {
        loadTemplates()
    }

    fun loadTemplates() {
        viewModelScope.launch {
            _templates.value = repo.getTemplates()
        }
    }

    fun updateTemplate(template: MessageTemplateDto) {
        viewModelScope.launch {
            repo.updateTemplate(template)
            loadTemplates()
        }
    }
}
