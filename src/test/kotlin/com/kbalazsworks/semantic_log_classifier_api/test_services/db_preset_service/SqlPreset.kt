package com.kbalazsworks.semantic_log_classifier_api.test_services.db_preset_service

import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class SqlPreset(
    val presets: Array<KClass<out IInsert>> = [],
    val truncateBefore: Boolean = true,
    val truncateAfter: Boolean = true
)

