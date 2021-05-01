package com.ratcoding.transfer.commission

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean
import org.springframework.core.io.Resource
import com.fasterxml.jackson.annotation.JsonInclude

import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule

import org.springframework.context.annotation.Primary
import java.time.LocalDateTime
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.text.SimpleDateFormat

import java.text.DateFormat
import java.util.*


@Configuration
class TransactionConfiguration() {

    @Value("classpath:transactions.json")
    private lateinit var transactionsJson: Resource

    @Value("classpath:fee.json")
    private lateinit var feeJson: Resource

    @Bean
    @Autowired
    fun populateTransaction(objectMapper: ObjectMapper): Jackson2RepositoryPopulatorFactoryBean {
        val factory = Jackson2RepositoryPopulatorFactoryBean()
        factory.setMapper(objectMapper)
        factory.setResources(arrayOf(transactionsJson,feeJson))
        return factory
    }
}
