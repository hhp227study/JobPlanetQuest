package com.hhp227.jobplanetquest.ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.hhp227.jobplanetquest.data.MainRepository
import com.hhp227.jobplanetquest.model.CellItem
import com.hhp227.jobplanetquest.model.RecruitItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    mainRepository: MainRepository
) : ViewModel() {
    var tabPosition by mutableStateOf(0)

    var queryText = MutableStateFlow("")

    private val _recruitItemList = mainRepository.getRecruitItemList()
    val recruitItemList: Flow<List<RecruitItem>> get() = queryText.flatMapLatest { query ->
        _recruitItemList.map { list ->
            list.filter { it.title.contains(query) || it.appeal.contains(query) }
        }
            .catch {
                emit(emptyList())
            }
    }

    private val _cellItemList = mainRepository.getCellItemList()
    val cellItemList: Flow<List<CellItem>> get() = queryText.flatMapLatest { query ->
        _cellItemList.map { list ->
            list.filter {
                (it as? CellItem.CellTypeCompany)?.let { cellItem ->
                    cellItem.name.contains(query)
                            || cellItem.industryName.contains(query)
                            || cellItem.interviewQuestion.contains(query)
                            || cellItem.reviewSummary.contains(query)
                } ?: true
            }
        }.catch {
            emit(emptyList())
        }
    }

    fun setQueryText(query: String) {
        queryText.value = query
    }

    fun onQuerySubmit() {
        //mainRepository.getTest()
    }
}