package com.kbalazsworks.semantic_log_classifier_api.api.controllers.log_controller

import com.kbalazsworks.semantic_log_classifier_api.api.builders.ResponseEntityBuilder
import com.kbalazsworks.semantic_log_classifier_api.domain.entities.VectorStoreXSimilarity
import com.kbalazsworks.semantic_log_classifier_api.domain.services.LogService
import com.kbalazsworks.semantic_log_classifier_api.domain.value_objects.EmbeddingRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/log/classification")
class ClassificationAction(val logService: LogService) {
    @PostMapping(consumes = ["application/json"], produces = ["application/json"])
    fun post(@RequestBody request: Request) = ResponseEntityBuilder<List<VectorStoreXSimilarity>>().apply {
        data = logService.classify(map(request))
        build()
    }

    private fun map(request: Request) = EmbeddingRequest(request.rawMessage)

    data class Request(val level: String, val rawMessage: String, val structuredMessage: String)
}
