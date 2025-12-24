package com.kbalazsworks.semantic_log_classifier_api.test_services.db_preset_service

import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.InvocationInterceptor
import org.junit.jupiter.api.extension.ReflectiveInvocationContext
import org.slf4j.LoggerFactory
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.lang.reflect.Method

class SqlPresetExtension : InvocationInterceptor {

    companion object {
        private val logger = LoggerFactory.getLogger(SqlPresetExtension::class.java)
    }

    override fun interceptTestMethod(
        invocation: InvocationInterceptor.Invocation<Void>,
        invocationContext: ReflectiveInvocationContext<Method>,
        extensionContext: ExtensionContext
    ) {
        executeWithPreset(invocation, invocationContext, extensionContext)
    }

    override fun interceptTestTemplateMethod(
        invocation: InvocationInterceptor.Invocation<Void>,
        invocationContext: ReflectiveInvocationContext<Method>,
        extensionContext: ExtensionContext
    ) {
        executeWithPreset(invocation, invocationContext, extensionContext)
    }

    private fun executeWithPreset(
        invocation: InvocationInterceptor.Invocation<Void>,
        context: ReflectiveInvocationContext<Method>,
        extensionContext: ExtensionContext
    ) {
        val method = context.executable
        val annotation = method.getAnnotation(SqlPreset::class.java)

        if (annotation == null) {
            invocation.proceed()
            return
        }

        val presetService = SpringExtension.getApplicationContext(extensionContext)
            .getBean(PresetService::class.java)

        if (annotation.truncateBefore) {
            logger.info("Truncate before enabled for test: ${method.name}")
            presetService.truncate()
        }

        if (annotation.presets.isNotEmpty()) {
            presetService.setupDb(annotation.presets)
        }

        try {
            invocation.proceed()
        } finally {
            if (annotation.truncateAfter) {
                logger.info("Truncate after enabled for test: ${method.name}")
                presetService.truncate()
            }
        }
    }
}
