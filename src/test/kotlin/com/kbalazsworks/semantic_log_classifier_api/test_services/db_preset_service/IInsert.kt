package com.kbalazsworks.semantic_log_classifier_api.test_services.db_preset_service

import com.pgvector.PGvector
import org.jooq.impl.DSL

interface IInsert {
    fun runParent()
    fun run()
    fun toPgVectorField(embedding: PGvector?) = DSL.field("?::vector", PGvector::class.java, embedding?.toString())
}
