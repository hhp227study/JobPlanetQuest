package com.hhp227.jobplanetquest.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecruitItem(
    @SerialName("id") val id: Int,
    @SerialName("title") val title: String,
    @SerialName("reward") val reward: Int,
    @SerialName("appeal") val appeal: String,
    @SerialName("image_url") val imageUrl: String,
    @SerialName("company") val company: Company
) : java.io.Serializable {
    companion object {
        fun getDefaultValue() = RecruitItem(
            id = 0,
            title = "",
            reward = 0,
            appeal = "",
            imageUrl = "https://img.freepik.com/free-vector/hand-painted-watercolor-pastel-sky-background_23-2148902771.jpg?w=2000",
            company = Company("", "", emptyList())
        )
    }
}