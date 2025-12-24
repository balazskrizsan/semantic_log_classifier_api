package com.kbalazsworks.semantic_log_classifier_api.test_services

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import org.jooq.JSONB

class JsonService {
    companion object {
        val objectMapper: ObjectMapper = ObjectMapper()

        fun toJSONBField(contextInfo: Map<String, String>): JSONB {
            val jsonNode: ObjectNode = objectMapper.createObjectNode()

            contextInfo.forEach { (key, value) -> jsonNode.put(key, value) }

            return JSONB.valueOf(objectMapper.writeValueAsString(jsonNode))
        }
    }
}
