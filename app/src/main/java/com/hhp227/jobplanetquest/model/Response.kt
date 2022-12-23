package com.hhp227.jobplanetquest.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecruitResponse(
    @SerialName("recruit_items") val recruitItems: List<RecruitItem>
)

@Serializable
data class CellResponse(
    @SerialName("cell_items") val cellItems: List<CellItem>
)