package com.kbalazsworks.semantic_log_classifier_api.domain.value_objects

import com.kbalazsworks.ssp_semantic_log_classifier_api.domain.ai_module.value_objects.SimilarityCondition

data class SimilaritySearchConfig(
    val similarityCondition: SimilarityCondition,
    val masterVector: FloatArray,
    val similarityLimit: Float,
    val queryLimit: Long
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SimilaritySearchConfig

        if (similarityLimit != other.similarityLimit) return false
        if (queryLimit != other.queryLimit) return false
        if (similarityCondition != other.similarityCondition) return false
        if (!masterVector.contentEquals(other.masterVector)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = similarityLimit.hashCode()
        result = 31 * result + queryLimit.hashCode()
        result = 31 * result + similarityCondition.hashCode()
        result = 31 * result + masterVector.contentHashCode()
        return result
    }
}
