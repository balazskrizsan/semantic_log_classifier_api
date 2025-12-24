package com.kbalazsworks.semantic_log_classifier_api

import com.kbalazsworks.semantic_log_classifier_api.domain.services.JooqService
import com.kbalazsworks.semantic_log_classifier_api.test_services.db_preset_service.SqlPresetExtension
import com.kbalazsworks.semantic_log_classifier_api.test_services.service_factory.ServiceFactory
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.provider.Arguments
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import java.util.function.Consumer
import java.util.stream.Stream

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = [SemanticLogClassifierApi::class])
@ExtendWith(SqlPresetExtension::class)
abstract class AbstractTest {
    @Autowired
    private lateinit var serviceFactory: ServiceFactory

    @Autowired
    private lateinit var jooqService: JooqService

    @AfterEach
    fun after() {
        serviceFactory.resetMockContainer()
    }

    companion object {
        @JvmStatic
        protected fun <T> providerMap(vararg dataList: T): Stream<Arguments> = dataList.map { testData ->
            Arguments.of(testData)
        }.stream()
    }

    protected fun <T> createInstance(clazz: Class<T>, mocks: List<Any>): T {
        mocks.forEach(Consumer { m: Any -> setOneTimeMock(clazz, m) })

        return createInstance(clazz)
    }

    protected fun <T> createInstance(clazz: Class<T>, mock: Any): T {
        setOneTimeMock(clazz, mock)

        return createInstance(clazz)
    }

    protected fun <T> createInstance(clazz: Class<T>): T {
        return serviceFactory.createInstance(clazz)
    }

    protected fun setOneTimeMock(newClass: Class<*>, mock: Any) {
        serviceFactory.setOneTimeMock(newClass, mock)
    }

    protected fun getDSLContext() = jooqService.getDslContext()
}
