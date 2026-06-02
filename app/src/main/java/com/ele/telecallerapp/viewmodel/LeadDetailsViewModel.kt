
package com.ele.telecallerapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ele.telecallerapp.data.repository.LeadRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LeadDetailsViewModel(
    private val repo: LeadRepository
) : ViewModel() {

    private val _lead = MutableStateFlow<UiLead?>(null)
    val lead: StateFlow<UiLead?> = _lead

    private val _activities = MutableStateFlow<List<UiActivityItem>>(emptyList())
    val activities: StateFlow<List<UiActivityItem>> = _activities

    private var leadId: String? = null

    fun load(id: String) {
        leadId = id
        viewModelScope.launch {
            val dto = repo.getLead(id)
            _lead.value = UiLead(
                id = dto._id,
                name = dto.name,
                phone = dto.phone,
                status = dto.status,
                company = dto.company,
                imageUrl = dto.imageUrl,
                createdAt = dto.createdAt,
                lastActivityAt = dto.lastActivityAt
            )
            refreshActivities()
        }
    }

    private suspend fun refreshActivities() {
        leadId?.let { id ->
            val list = repo.getActivities(id)
            _activities.value = list.map {
                UiActivityItem(
                    id = it._id,
                    type = it.type,
                    description = it.description ?: "",
                    time = it.createdAt
                )
            }
        }
    }

    fun addNote(text: String) {
        val id = leadId ?: return
        viewModelScope.launch {
            repo.addActivity(id, "Note", text)
            refreshActivities()
        }
    }

    fun logCall() {
        val id = leadId ?: return
        viewModelScope.launch {
            repo.addActivity(id, "Call", null)
            refreshActivities()
        }
    }

    fun logWhatsApp() {
        val id = leadId ?: return
        viewModelScope.launch {
            repo.addActivity(id, "WhatsApp", null)
            refreshActivities()
        }
    }

    fun logSms() {
        val id = leadId ?: return
        viewModelScope.launch {
            repo.addActivity(id, "SMS", null)
            refreshActivities()
        }
    }
}
