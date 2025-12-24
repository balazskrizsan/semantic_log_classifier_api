package com.kbalazsworks.semantic_log_classifier_api.domain.entities

import com.pgvector.PGvector

data class VectorStoreX(
    val id: Long?,
    val vectorModelId: Long,
    val contextInfo: Map<String, String>,
    val embedding: PGvector
)
