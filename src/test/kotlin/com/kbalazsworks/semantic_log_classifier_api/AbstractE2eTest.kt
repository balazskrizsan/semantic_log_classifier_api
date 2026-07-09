package com.kbalazsworks.semantic_log_classifier_api

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

abstract class AbstractE2eTest : AbstractTest() {
    @Autowired
    protected var wac: WebApplicationContext? = null

    fun getMockMvc() = getMockMvc(false)

    fun getMockMvc(apOverride: Boolean) = MockMvcBuilders.webAppContextSetup(this.wac).build()

//    fun getMockMvcWithSecurity() =  MockMvcBuilders
//        .webAppContextSetup(this.wac)
//        .apply(springSecurity())
//        .build()
}
