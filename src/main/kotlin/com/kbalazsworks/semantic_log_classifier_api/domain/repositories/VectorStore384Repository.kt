package com.kbalazsworks.semantic_log_classifier_api.domain.repositories

import com.kbalazsworks.semantic_log_classifier_api.db.tables.references.VECTOR_STORE_384
import com.kbalazsworks.semantic_log_classifier_api.domain.entities.VectorStoreX
import com.kbalazsworks.semantic_log_classifier_api.domain.entities.VectorStoreXSimilarity
import com.kbalazsworks.semantic_log_classifier_api.domain.services.JooqService
import com.kbalazsworks.semantic_log_classifier_api.domain.value_objects.SimilaritySearchConfig
import com.pgvector.PGvector
import org.jooq.impl.DSL
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository

@Repository
class VectorStore384Repository(private val jooqService: JooqService) : AbstractRepository(jooqService) {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
        private val store = VECTOR_STORE_384;
    }

    fun save(vectorStoreX: VectorStoreX) = jooqService.getDslContext()
        .insertInto(store)
        .set(store.VECTOR_MODEL_ID, vectorStoreX.vectorModelId)
        .set(store.EMBEDDING, toPgVectorField(vectorStoreX.embedding))
        .set(store.CONTEXT_INFO, toJSONBField(vectorStoreX.contextInfo))
        .returning()
        .fetchOneInto(VectorStoreX::class.java)
        ?: throw Exception("VectorStoreX creation failed.")

    fun similaritySearch(config: SimilaritySearchConfig) = getCtx()
        .select(store.asterisk(), embedding384comparator(config.masterVector))
        .from(store)
        .where(buildSimilarityConditions(config.similarityCondition.conditions))
        .orderBy(DSL.field("similarity").desc())
        .limit(config.queryLimit)
        .fetch()
        .filter { record -> record.get("similarity", Float::class.java) >= config.similarityLimit }
        .mapNotNull { record ->
            val embedding = record.into(VectorStoreX::class.java)
            val similarity = record.get("similarity", Float::class.java)
            VectorStoreXSimilarity(similarity, embedding)
        }

    private fun embedding384comparator(masterVector: FloatArray) = DSL.field(
        "1 - ({0} <=> {1})",
        Float::class.java,
        store.EMBEDDING,
        toPgVectorField(PGvector(masterVector))
    ).`as`("similarity")
}
