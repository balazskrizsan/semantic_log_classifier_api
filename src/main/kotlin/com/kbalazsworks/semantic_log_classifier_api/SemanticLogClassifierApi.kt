package com.kbalazsworks.semantic_log_classifier_api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class SemanticLogClassifierApi

fun main(args: Array<String>) {
    runApplication<SemanticLogClassifierApi>(*args)
}
