package com.kbalazsworks.semantic_log_classifier_api.db_presets

import com.kbalazsworks.semantic_log_classifier_api.db.tables.references.VECTOR_STORE_384
import com.kbalazsworks.semantic_log_classifier_api.domain.services.JooqService
import com.kbalazsworks.semantic_log_classifier_api.fake_builders.VectorStore384FakeBuilder
import com.kbalazsworks.semantic_log_classifier_api.test_services.JsonService.Companion.toJSONBField
import com.kbalazsworks.semantic_log_classifier_api.test_services.db_preset_service.IInsert
import com.kbalazsworks.semantic_log_classifier_api.test_services.db_preset_service.PresetRunnerService
import org.springframework.stereotype.Component

@Component
@Suppress("ClassName")
class VectorStore384_Insert9rows_TypeLog(
    private val presetRunnerService: PresetRunnerService,
    private val jooqService: JooqService
) : IInsert {
    override fun runParent() {
        presetRunnerService.run(VectorModels_Insert1row::class.java)
    }

    override fun run() {
        val vectorStoreXList = listOf(
            VectorStore384FakeBuilder().build_realVector1(),
            VectorStore384FakeBuilder().build_realVector2(),
            VectorStore384FakeBuilder().build_realVector3(),
            VectorStore384FakeBuilder().build_realVector4(),
            VectorStore384FakeBuilder().build_realVector5(),
            VectorStore384FakeBuilder().build_realVector6(),
            VectorStore384FakeBuilder().build_realVector7(),
            VectorStore384FakeBuilder().build_realVector8(),
            VectorStore384FakeBuilder().build_realVector9(),
        )

        for (vectorStoreX in vectorStoreXList) {
            jooqService.getDslContext()
                .insertInto(VECTOR_STORE_384)
                .set(VECTOR_STORE_384.ID, vectorStoreX.id)
                .set(VECTOR_STORE_384.VECTOR_MODEL_ID, vectorStoreX.vectorModelId)
                .set(VECTOR_STORE_384.CONTEXT_INFO, toJSONBField(vectorStoreX.contextInfo))
                .set(VECTOR_STORE_384.EMBEDDING, toPgVectorField(vectorStoreX.embedding))
                .execute()
        }
    }
}
