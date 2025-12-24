package com.kbalazsworks.semantic_log_classifier_api.domain.config

import dev.langchain4j.model.embedding.EmbeddingModel
import dev.langchain4j.model.embedding.onnx.allminilml6v2.AllMiniLmL6V2EmbeddingModel
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class EmbeddingConfig {
    @Bean
    fun allMiniLmL6V2EmbeddingModel(): EmbeddingModel {
        return AllMiniLmL6V2EmbeddingModel()
    }
}
