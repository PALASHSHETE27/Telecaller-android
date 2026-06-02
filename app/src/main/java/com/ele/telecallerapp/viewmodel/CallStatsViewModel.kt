package com.ele.telecallerapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.ele.telecallerapp.data.repository.CallStatsRepository
import kotlinx.coroutines.flow.StateFlow
import com.ele.telecallerapp.network.CallStats

class CallStatsViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = CallStatsRepository(app)
    val stats: StateFlow<CallStats> = repo.stats
}
