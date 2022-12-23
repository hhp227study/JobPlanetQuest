package com.hhp227.jobplanetquest.ui.detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.hhp227.jobplanetquest.model.CellItem
import com.hhp227.jobplanetquest.model.RecruitItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val recruitItem: Flow<RecruitItem?> get() = savedStateHandle.getStateFlow("item", null)

    val cellItem: Flow<CellItem?> get() = savedStateHandle.getStateFlow("cell", null)

    init {
        savedStateHandle.get<String>("recruitItem")?.also { string ->
            savedStateHandle["item"] = Json.decodeFromJsonElement<RecruitItem>(Json.parseToJsonElement(string))
        }
        savedStateHandle.get<String>("cellItem")?.also { string ->
            savedStateHandle["cell"] = Json.decodeFromJsonElement<CellItem>(Json.parseToJsonElement(string))
        }
    }
}