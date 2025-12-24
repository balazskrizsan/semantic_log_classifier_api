package com.kbalazsworks.semantic_log_classifier_api.domain.value_objects

data class ClassificationConfig(
    val similarityLimit: Float,
    val queryLimit: Long
)
