package com.hhp227.jobplanetquest.util

import com.hhp227.jobplanetquest.model.CellItem
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

object CellItemSerializer : JsonContentPolymorphicSerializer<CellItem>(CellItem::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<out CellItem> {
        return when (element.jsonObject["cell_type"]?.jsonPrimitive?.content) {
            "CELL_TYPE_COMPANY" -> CellItem.CellTypeCompany.serializer()
            "CELL_TYPE_HORIZONTAL_THEME" -> CellItem.CellTypeHorizontalTheme.serializer()
            "CELL_TYPE_REVIEW" -> CellItem.CellTypeReview.serializer()
            else -> throw Exception("Unknown Module: key 'type' not found or does not matches any module type")
        }
    }
}

enum class CellType {
    CELL_TYPE_COMPANY, CELL_TYPE_HORIZONTAL_THEME, CELL_TYPE_REVIEW
}