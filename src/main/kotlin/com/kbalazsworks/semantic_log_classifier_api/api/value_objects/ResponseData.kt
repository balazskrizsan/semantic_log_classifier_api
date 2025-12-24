package com.kbalazsworks.semantic_log_classifier_api.api.value_objects

data class ResponseData<T>(
    val data: T?,
    val success: Boolean,
    val errorCode: Int,
)
