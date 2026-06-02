

package com.ele.telecallerapp.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ele.telecallerapp.data.repository.LeadRepository
import kotlinx.coroutines.launch

class AddLeadViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = LeadRepository(app)

    fun create(
        name: String,
        email: String?,
        phone: String,
        status: String,
        company: String?,
        description: String?,
        imageUri: Uri?,
        onDone: () -> Unit
    ) {
        viewModelScope.launch {
            repo.createLead(
                name = name,
                email = email,
                phone = phone,
                status = status,
                company = company,
                campaign = null,
                source = null,
                priority = null,
                description = description,
                imageUri = imageUri
            )
            onDone()
        }
    }
}
