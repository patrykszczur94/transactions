package com.ratcoding.transfer.commission

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import java.math.BigDecimal
import java.time.Instant

interface TransactionRepository : MongoRepository<Transaction, String> {
    fun findAllByCustomerIdIn(customerId: List<String>): List<Transaction>
}
interface FeeRepository : MongoRepository<FeeTable, String> {
    // TODO: limit(1)
    @Query(value  = " {value : {\$gt: ?0}} " , sort = "{value : 1}")
    fun findSortedFeesForGivenAmount(value: Long) : List<FeeTable>
}

interface LogRepository : MongoRepository<LogEntity, String>

@Document("log")
data class LogEntity(val customerId: String, val date: Instant, val feeValue: BigDecimal)
