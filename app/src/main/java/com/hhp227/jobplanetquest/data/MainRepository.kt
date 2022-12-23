package com.hhp227.jobplanetquest.data

import com.hhp227.jobplanetquest.api.MainService
import com.hhp227.jobplanetquest.model.CellItem
import com.hhp227.jobplanetquest.model.RecruitItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(private val apiService: MainService) {
    fun getRecruitItemList(): Flow<List<RecruitItem>> = flow {
        emit(apiService.getTestDataRecruitItems().recruitItems)
    }

    fun getCellItemList(): Flow<List<CellItem>> = flow {
        emit(apiService.getTestDataCellItems().cellItems)
    }
}