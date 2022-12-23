package com.hhp227.jobplanetquest.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Company(
    @SerialName("name") val name: String,
    @SerialName("logo_path") val logoPath: String,
    @SerialName("ratings") val ratings: List<Rating>
)

@Serializable
data class Rating(
    @SerialName("type") val type: String,
    @SerialName("rating") val rating: Double
)