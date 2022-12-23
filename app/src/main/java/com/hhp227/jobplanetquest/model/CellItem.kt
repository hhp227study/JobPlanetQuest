package com.hhp227.jobplanetquest.model

import com.hhp227.jobplanetquest.util.CellItemSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(with = CellItemSerializer::class)
sealed class CellItem : java.io.Serializable {
    abstract val cellType: String

    @Serializable
    data class CellTypeCompany(
        @SerialName("cell_type") override val cellType: String,
        @SerialName("logo_path") val logoPath: String,
        @SerialName("name") val name: String,
        @SerialName("industry_name") val industryName: String,
        @SerialName("rate_total_avg") val rateTotalAvg: Double,
        @SerialName("review_summary") val reviewSummary: String,
        @SerialName("interview_question") val interviewQuestion: String,
        @SerialName("salary_avg") val salaryAvg: Int,
        @SerialName("update_date") val updateDate: String
    ) : CellItem() {
        companion object {
            fun getDefaultValue() = CellTypeCompany(
                cellType = "",
                logoPath = "",
                name = "",
                industryName = "",
                rateTotalAvg = 0.0,
                reviewSummary = "",
                interviewQuestion = "",
                salaryAvg = 0,
                updateDate = ""
            )
        }
    }

    @Serializable
    data class CellTypeHorizontalTheme(
        @SerialName("cell_type") override val cellType: String,
        @SerialName("count") val count: Int,
        @SerialName("section_title") val sectionTitle: String,
        @SerialName("recommend_recruit") val recommendRecruit: List<RecruitItem>
    ) : CellItem() {
        companion object {
            fun getDefaultValue() = CellTypeHorizontalTheme(
                cellType = "",
                count = 0,
                sectionTitle = "",
                recommendRecruit = emptyList()
            )
        }
    }

    @Serializable
    data class CellTypeReview(
        @SerialName("cell_type") override val cellType: String,
        @SerialName("logo_path") val logoPath: String,
        @SerialName("name") val name: String,
        @SerialName("industry_name") val industryName: String,
        @SerialName("rate_total_avg") val rateTotalAvg: Double,
        @SerialName("review_summary") val reviewSummary: String,
        @SerialName("cons") val cons: String,
        @SerialName("pros") val pros: String,
        @SerialName("update_date") val updateDate: String
    ) : CellItem() {
        companion object {
            fun getDefaultValue() = CellTypeReview(
                cellType = "",
                logoPath = "",
                name = "",
                industryName = "",
                rateTotalAvg = 0.0,
                reviewSummary = "",
                cons = "",
                pros = "",
                updateDate = ""
            )
        }
    }

    object Loader : CellItem() {
        override val cellType: String
            get() = "Loader"
    }
}
