package com.kbalazsworks.semantic_log_classifier_api.fake_builders

import com.kbalazsworks.semantic_log_classifier_api.domain.entities.VectorModel

class VectorModelFakeBuilder {
    companion object {
        val DEFAULT_ID = 1L // 101000L will be updated when config coming from db
        val DEFAULT_NAME = "DEFAULT embedded model name"
    }

    private var id = DEFAULT_ID
    private var name = DEFAULT_NAME

    fun build(): VectorModel {
        return VectorModel(id, name)
    }
}
