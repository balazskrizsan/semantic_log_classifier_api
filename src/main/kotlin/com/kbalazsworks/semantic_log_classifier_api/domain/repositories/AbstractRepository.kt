package com.kbalazsworks.semantic_log_classifier_api.domain.repositories

import com.fasterxml.jackson.databind.ObjectMapper
import com.kbalazsworks.semantic_log_classifier_api.domain.services.JooqService
import com.kbalazsworks.ssp_semantic_log_classifier_api.domain.ai_module.value_objects.SimilarityConditionField
import com.pgvector.PGvector
import org.jooq.JSONB
import org.jooq.impl.DSL
import org.springframework.stereotype.Repository

@Repository
abstract class AbstractRepository(private val jooqService: JooqService) {
    companion object {
        val objectMapper = ObjectMapper()
    }

    fun getCtx() = jooqService.getDslContext()

    fun toPgVectorField(embedding: PGvector?) = DSL.field("?::vector", PGvector::class.java, embedding?.toString())

    fun toJSONBField(contextInfo: Map<String, String>) = JSONB.valueOf(
        objectMapper.writeValueAsString(
            objectMapper.createObjectNode().apply {
                contextInfo.forEach { (k, v) -> put(k, v) }
            }
        )
    )

    fun buildSimilarityConditions(fields: List<SimilarityConditionField>) = DSL.and(
        fields.map { field ->
            val type = field.type
            val typeValue = field.typeValue
            val psqlTypeCast = field.psqlTypeCast

            val typeObject = when (psqlTypeCast) {
                "bigint" -> Long::class
                else -> String::class
            }

            val typeValueCasted = when (psqlTypeCast) {
                "bigint" -> typeValue.toLong()
                else -> typeValue
            }

            val newField = when {
                psqlTypeCast == null || psqlTypeCast == "string" -> DSL.field("context_info->>'$type'", typeObject)
                else -> DSL.field("(context_info->>'$type')::$psqlTypeCast", typeObject)
            }

            newField.eq(typeValueCasted)
        }
    )
}
