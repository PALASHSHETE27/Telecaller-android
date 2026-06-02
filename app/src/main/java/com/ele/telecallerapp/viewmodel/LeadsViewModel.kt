
package com.ele.telecallerapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ele.telecallerapp.data.repository.LeadRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LeadsViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = LeadRepository(app)

    private val _leads = MutableStateFlow<List<UiLead>>(emptyList())
    val leads: StateFlow<List<UiLead>> = _leads

    init {
        refresh()
    }

    fun refresh(
        status: String? = null,
        search: String? = null
    ) {
        viewModelScope.launch {
            repo.observeLeads(
                page = 1,
                status = status,
                search = search
            ).collect { list ->

                _leads.value = list.map {
                    UiLead(
                        id = it._id,
                        name = it.name,
                        phone = it.phone,
                        status = it.status,
                        company = it.company,
                        imageUrl = it.imageUrl,
                        createdAt = it.createdAt,
                        lastActivityAt = it.lastActivityAt
                    )
                }
            }
        }
    }

    // ✅ DELETE LEAD
    fun deleteLead(
        id: String,
        status: String? = null,
        search: String? = null
    ) {
        viewModelScope.launch {

            repo.deleteLead(id)

            refresh(status, search)
        }
    }
}