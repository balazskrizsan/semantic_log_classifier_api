package com.kbalazsworks.ssp_semantic_log_classifier_api.domain.ai_module.value_objects

data class SimilarityConditionField(
    val type: String,
    val typeValue: String,
    val psqlTypeCast: String? = null,
)
