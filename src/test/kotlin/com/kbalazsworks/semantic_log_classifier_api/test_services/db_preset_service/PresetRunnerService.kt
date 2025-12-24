package com.kbalazsworks.semantic_log_classifier_api.test_services.db_preset_service

import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Service

@Service
class PresetRunnerService(private val applicationContext: ApplicationContext) {
    fun run(preset: Class<out IInsert>) {
        val instance: IInsert = applicationContext.getBean(preset)

        instance.runParent()
        instance.run()
    }
}
