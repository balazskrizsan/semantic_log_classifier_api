package com.kbalazsworks.semantic_log_classifier_api.domain.services

import com.kbalazsworks.semantic_log_classifier_api.domain.entities.VectorStoreX
import com.kbalazsworks.semantic_log_classifier_api.domain.entities.VectorStoreXSimilarity
import com.kbalazsworks.semantic_log_classifier_api.domain.repositories.VectorStore384Repository
import com.kbalazsworks.semantic_log_classifier_api.domain.value_objects.ClassificationConfig
import com.kbalazsworks.semantic_log_classifier_api.domain.value_objects.EmbeddingRequest
import com.kbalazsworks.semantic_log_classifier_api.domain.value_objects.SimilaritySearchConfig
import com.kbalazsworks.ssp_semantic_log_classifier_api.domain.ai_module.value_objects.SimilarityCondition
import com.kbalazsworks.ssp_semantic_log_classifier_api.domain.ai_module.value_objects.SimilarityConditionField
import com.pgvector.PGvector
import dev.langchain4j.model.embedding.EmbeddingModel
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class LogService(
    private val embeddingModel: EmbeddingModel,
    private val vectorStore384Repository: VectorStore384Repository
) {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
        private val classificationConfig = ClassificationConfig(0.8F, 1L)
    }

    fun classify(embeddingRequest: EmbeddingRequest): List<VectorStoreXSimilarity> {
        log.info("Classification started: {}", embeddingRequest)
        log.info("Classification - embedding started: {}", embeddingRequest.text)

        val embeddingResponse = embeddingModel.embed(embeddingRequest.text)

        log.info("Classification - embedding finished: {}", embeddingResponse)
        log.info("Classification - similarity search started: {}", embeddingResponse)

        var similarityResponse = vectorStore384Repository.similaritySearch(
            SimilaritySearchConfig(
                SimilarityCondition(
                    listOf(
                        SimilarityConditionField("type", "log")
                    )
                ),
                embeddingResponse.content().vector(),
                classificationConfig.similarityLimit,
                classificationConfig.queryLimit
            )
        )

        log.info("Classification - similarity search finished: {}", embeddingResponse)

        if (similarityResponse.isEmpty()) {
            similarityResponse = listOf(
                VectorStoreXSimilarity(
                    1F,
                    vectorStore384Repository.save(
                        VectorStoreX(
                            null,
                            1L,
                            mapOf("type" to "log", "text" to embeddingRequest.text),
                            PGvector(embeddingResponse.content().vector())
                        )
                    )
                )
            )

            log.info("Classification - mew log created")
        }

        log.info("Classification finished: {}", similarityResponse)

        return similarityResponse
    }

    fun createAndSaveEmbedding(request: EmbeddingRequest) = embeddingModel.embed(request.text).run {
        log.info("Create embed: {}", request)

        vectorStore384Repository.save(
            VectorStoreX(null, 1L, mapOf("type" to "log", "text" to request.text), PGvector(content().vector()))
        )
    }
}
