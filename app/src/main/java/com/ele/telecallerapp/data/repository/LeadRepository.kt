
package com.ele.telecallerapp.data.repository

import android.content.Context
import android.net.Uri
import com.ele.telecallerapp.network.*
import com.ele.telecallerapp.utils.uriToFile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class LeadRepository(private val context: Context) {

    private val api = RetrofitClient.api

    /* ---------------- FETCH ---------------- */

    fun observeLeads(
        page: Int = 1,
        status: String?,
        search: String?
    ): Flow<List<LeadDto>> = flow {
        emit(api.getLeads(status, page, 20, search).data)
    }

    suspend fun getLead(id: String): LeadDto {
        return api.getLeadById(id).data
    }

    suspend fun deleteLead(id: String) {
        api.deleteLead(id)
    }

    /* ---------------- ACTIVITIES ---------------- */

    suspend fun getActivities(leadId: String): List<ActivityDto> {
        return api.getLeadActivities(leadId).data
    }

    suspend fun addActivity(leadId: String, type: String, desc: String?) {
        api.createActivity(leadId, CreateActivityRequest(type, desc))
    }

    /* ---------------- CREATE ---------------- */

    suspend fun createLead(
        name: String,
        email: String?,
        phone: String,
        status: String,
        company: String?,
        campaign: String?,
        source: String?,
        priority: String?,
        description: String?,
        imageUri: Uri?
    ): LeadDto {

        if (imageUri == null) {
            return api.createLead(
                CreateLeadRequest(
                    name = name,
                    email = email,
                    phone = phone,
                    status = status,
                    company = company,
                    campaign = campaign,
                    source = source,
                    priority = priority,
                    description = description
                )
            ).data
        }

        return api.createLeadWithImage(
            name = name.toPart(),
            email = email?.toPart(),
            phone = phone.toPart(),
            status = status.toPart(),
            company = company?.toPart(),
            campaign = campaign?.toPart(),
            source = source?.toPart(),
            priority = priority?.toPart(),
            description = description?.toPart(),
            image = createImagePart(imageUri)
        ).data
    }

    /* ---------------- UPDATE ---------------- */

    suspend fun updateLead(
        id: String,
        name: String?,
        email: String?,
        phone: String?,
        status: String?,
        company: String?,
        campaign: String?,
        source: String?,
        priority: String?,
        description: String?,
        imageUri: Uri?
    ): LeadDto {

        if (imageUri == null) {
            return api.updateLead(
                id,
                UpdateLeadRequest(
                    name = name,
                    email = email,
                    phone = phone,
                    status = status,
                    company = company,
                    campaign = campaign,
                    source = source,
                    priority = priority,
                    description = description
                )
            ).data
        }

        return api.updateLeadWithImage(
            id = id,
            name = name?.toPart(),
            email = email?.toPart(),
            phone = phone?.toPart(),
            status = status?.toPart(),
            company = company?.toPart(),
            campaign = campaign?.toPart(),
            source = source?.toPart(),
            priority = priority?.toPart(),
            description = description?.toPart(),
            image = createImagePart(imageUri)
        ).data
    }

    /* ---------------- HELPERS ---------------- */

    private fun createImagePart(uri: Uri): MultipartBody.Part {
        val file = uriToFile(context, uri)
        val body = file.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("image", file.name, body)
    }

    private fun String.toPart(): RequestBody =
        this.toRequestBody("text/plain".toMediaTypeOrNull())
}
