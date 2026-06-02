

package com.ele.telecallerapp.data.repository

import android.content.Context
import com.ele.telecallerapp.network.CampaignDto
import com.ele.telecallerapp.network.RetrofitClient

class CampaignRepository(context: Context) {

    private val api = RetrofitClient.api

    suspend fun getCampaigns(): List<CampaignDto> {
        return api.getCampaigns()
    }

    suspend fun createCampaign(
        title: String,
        audience: String,
        total: Int,
        called: Int,
        start: String,
        due: String,
        status: String
    ) {
        api.createCampaign(
            mapOf(
                "title" to title,
                "audience" to audience,
                "totalLeads" to total,
                "calledLeads" to called,
                "startDate" to start,
                "dueDate" to due,
                "status" to status
            )
        )
    }

    suspend fun deleteCampaign(id: String) {
        api.deleteCampaign(id)
    }
}
