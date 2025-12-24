package com.kbalazsworks.semantic_log_classifier_api.api.controllers.log_controller

import com.kbalazsworks.semantic_log_classifier_api.domain.services.LogService
import com.kbalazsworks.semantic_log_classifier_api.domain.value_objects.EmbeddingRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/log")
class PostAction(val logService: LogService) {
    @PostMapping(consumes = ["application/json"], produces = ["application/json"])
    fun post(@RequestBody request: Request) {
        logService.createAndSaveEmbedding(map(request))
    }

    private fun map(request: Request) = EmbeddingRequest(request.structuredMessage)

    @JvmRecord
    data class Request(val level: String, val rawMessage: String, val structuredMessage: String)
}
