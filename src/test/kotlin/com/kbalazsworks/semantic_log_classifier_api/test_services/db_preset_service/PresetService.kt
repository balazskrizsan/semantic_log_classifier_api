package com.kbalazsworks.semantic_log_classifier_api.test_services.db_preset_service

import com.kbalazsworks.semantic_log_classifier_api.db.Public.Companion.PUBLIC
import com.kbalazsworks.semantic_log_classifier_api.domain.services.JooqService
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Service
import kotlin.reflect.KClass

@Service
class PresetService(
    private val applicationContext: ApplicationContext,
    private val jooqService: JooqService,
) {
    fun truncate() {
        PUBLIC.getTables()
            .filter { t -> !t.asTable().getName().contains("flyway_schema_history") }
            .forEach { t -> jooqService.getDslContext().truncate(t).restartIdentity().cascade().execute() }
    }

    fun setupDb(presetClasses: Array<KClass<out IInsert>>) {
        presetClasses.forEach { preset ->
            run {
                val instance: IInsert = applicationContext.getBean(preset.java)

                instance.runParent()
                instance.run()
            }
        }
    }
}
