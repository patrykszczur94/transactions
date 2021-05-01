package com.ratcoding.transfer.commission

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.Instant
import java.time.Instant.now
import java.time.LocalDateTime
import java.util.*


@Service
class TransactionFacade(
    private val transactionService: TransactionService,
    private val transactionRepository: TransactionRepository) {
    private val logger = LoggerFactory.getLogger(javaClass)

    fun isInteger(str: String?) = str?.toIntOrNull()?.let { true } ?: false

    fun getTransaction(usersIds: List<String>) : Map<Int, List<UserResponseEntity>> {
        return if (usersIds.isEmpty() || usersIds.contains("ALL")) {
            transactionService.getTransactionsForUsers(transactionRepository.findAll())
        } else if (usersIds.any { !isInteger(it) }) {
            logger.info("incorrect input param in $usersIds")
            throw TransactionException("can,t find any customers")
        } else {
            transactionService.getTransactionsForUsers(transactionRepository.findAllByCustomerIdIn(usersIds))
        }
    }
}

@Service
class TransactionService(private val feeService: FeeService) {

    fun getTransactionsForUsers(customersForGivenIds: List<Transaction>): Map<Int, List<UserResponseEntity>> {
        if (customersForGivenIds.isEmpty()) {
            throw TransactionException("can,t find any customers")
        }
        val groupByCustomerId = customersForGivenIds.groupBy { it.customerId }
        val customerIdSumAllCustomerTransactionsMap = sumTransactionsForUsers(customersForGivenIds)
        val feeValuesForUsers = feeService.getFeeForGivenUserIdAndAmount(customerIdSumAllCustomerTransactionsMap)

        return customersForGivenIds.distinctBy { it.customerId }
            .mapTo(ArrayList<UserResponseEntity>()) { it ->
                UserResponseEntity(
                    it.name, it.surname, it.customerId,
                    groupByCustomerId[it.customerId]?.size, customerIdSumAllCustomerTransactionsMap[it.customerId],
                    calculateFee(customerIdSumAllCustomerTransactionsMap, it, feeValuesForUsers),
                    groupByCustomerId[it.customerId]?.maxByOrNull { it.transactionDate }!!.transactionDate
                )
            }
            .groupBy { groupByCustomerId.size }
    }

    private fun calculateFee(
        customerIdSumAllCustomerTransactionsMap: Map<String, BigDecimal>,
        it: Transaction,
        feeValue: Map<String, BigDecimal?>
    ): BigDecimal {
        val calculatedFeeValue = customerIdSumAllCustomerTransactionsMap[it.customerId]?.multiply(feeValue[it.customerId])
            ?.divide(BigDecimal(100)) ?: BigDecimal(0)
        feeService.saveLogEntity(it.customerId, now(), calculatedFeeValue)
        return calculatedFeeValue
    }

    private fun sumTransactionsForUsers(customersForGivenIds: List<Transaction>) =
        customersForGivenIds
            .groupBy { it.customerId }
            .mapValues { it -> it.value.sumOf { it.transactionAmount } }
}

@Service
class FeeService(private val feeRepository: FeeRepository, private val logRepository: LogRepository) {

    private val logger = LoggerFactory.getLogger(javaClass)

    fun getFeeForGivenUserIdAndAmount(transactionsAmountGroupedByCustomerId: Map<String, BigDecimal>): Map<String, BigDecimal?> {
        return transactionsAmountGroupedByCustomerId.mapValues { it ->
            val sortedFeesForAmount =
                feeRepository.findSortedFeesForGivenAmount(it.value.toLong()).takeIf { it.isNotEmpty() }
            sortedFeesForAmount?.first()?.percentage ?: run {
                logger.info("no fee for {} ", it.value)
                BigDecimal(0)
            }
        }
    }

    fun saveLogEntity(customerId: String, date: Instant, feeValue: BigDecimal) {
        logRepository.save(LogEntity(customerId,date, feeValue))
    }
}

data class UserResponseEntity(
    val name: String, val surname: String, val customerId: String,
    val numberOfTransactions: Int?, val totalTransactionValue: BigDecimal?,
    val feeValue: BigDecimal,
    val lastTransactionDate: LocalDateTime
)

class TransactionException(message: String) : Exception(message)
