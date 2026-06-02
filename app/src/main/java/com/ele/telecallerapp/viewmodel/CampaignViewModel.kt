
package com.ele.telecallerapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ele.telecallerapp.data.repository.CampaignRepository
import com.ele.telecallerapp.network.CampaignDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CampaignViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = CampaignRepository(app)

    private val _campaigns = MutableStateFlow<List<CampaignDto>>(emptyList())
    val campaigns: StateFlow<List<CampaignDto>> = _campaigns

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            try {
                _campaigns.value = repo.getCampaigns()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun create(
        title: String,
        audience: String,
        total: Int,
        called: Int,
        start: String,
        due: String,
        status: String
    ) {
        viewModelScope.launch {
            try {
                repo.createCampaign(title, audience, total, called, start, due, status)
                load()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun delete(id: String) {
        viewModelScope.launch {
            try {
                repo.deleteCampaign(id)
                load()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
