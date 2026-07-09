package com.kbalazsworks.semantic_log_classifier_api.domain.value_objects

import java.time.Instant

data class EmbeddingRequest(val text: String, val timestamp: Instant)
