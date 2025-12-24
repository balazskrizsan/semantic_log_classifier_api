package com.kbalazsworks.semantic_log_classifier_api.domain.services

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class ApplicationPropertiesService {
    @Value("\${server.env}") lateinit var serverEnv: String

    @Value("\${spring.datasource.url}") lateinit var springDatasourceUrl: String
    @Value("\${spring.datasource.username}") lateinit var springDatasourceUsername: String
    @Value("\${spring.datasource.password}") lateinit var springDatasourcePassword: String
    @Value("\${spring.datasource.driver-class-name}") lateinit var springDatasourceDriverClassName: String
}
