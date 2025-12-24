package com.kbalazsworks.semantic_log_classifier_api.db_presets

import com.kbalazsworks.semantic_log_classifier_api.db.tables.VectorModels
import com.kbalazsworks.semantic_log_classifier_api.domain.services.JooqService
import com.kbalazsworks.semantic_log_classifier_api.fake_builders.VectorModelFakeBuilder
import com.kbalazsworks.semantic_log_classifier_api.test_services.db_preset_service.IInsert
import com.kbalazsworks.semantic_log_classifier_api.test_services.db_preset_service.PresetRunnerService
import org.springframework.stereotype.Component

@Component
@Suppress("ClassName")
class VectorModels_Insert1row(
    private val presetRunnerService: PresetRunnerService,
    private val jooqService: JooqService
) : IInsert {
    override fun runParent() {
    }

    override fun run() {
        jooqService.getDslContext().newRecord(VectorModels.VECTOR_MODELS, VectorModelFakeBuilder().build()).store()
    }
}