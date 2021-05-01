package com.ratcoding.transfer.commission

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDateTime

internal class TransactionFacadeTest {
    private val mockLogRepo: LogRepository = Mockito.mock(LogRepository::class.java)
    private val testFeeService: FeeService = FeeService(InMemoryRepos.InMemoryFeeRepo(), mockLogRepo)
    private val transactionService: TransactionService = TransactionService(testFeeService)
    private val transactionHelper : TransactionFacade = TransactionFacade(transactionService, InMemoryRepos.InMemoryTransactionRepo())

    @Test
    fun shouldThrowExceptionWhenGivenIncorrectStringValue() {
        assertThrows<TransactionException> {
            val incorrectInput = listOf("ghzghz")
            transactionHelper.getTransaction(incorrectInput)
        }
    }

    @Test
    fun shouldReturnAllTransactionsWhenGivenAllParam() {
        //given
        val input = listOf("ALL")
        val numberOfTransactionInDB = 5

        //when
        val transactions = transactionHelper.getTransaction(input)
        //then
        assertEquals(transactions[5]?.size, numberOfTransactionInDB)
    }

    @Test
    fun shouldReturnAllTransactionsWhenGivenEmptyParam() {
        //given
        val input = emptyList<String>()
        val numberOfTransactionInDB = 5

        //when
        val transactions = transactionHelper.getTransaction(input)
        //then
        assertEquals(transactions[5]?.size, numberOfTransactionInDB)
    }

    @Test
    fun shouldReturnEqualNumberOfUsersAsResponseMapKey() {
        //given
        val input = emptyList<String>()
        val numberOfUsers = 5

        //when
        val transactions = transactionHelper.getTransaction(input)
        //then
        assertEquals(transactions.keys.first(), numberOfUsers)
    }

    @Test
    fun shouldReturnSingleTransactionForSelectedUser() {
        //given
        val input = listOf("2")

        //when
        val transactions = transactionHelper.getTransaction(input)
        //then
        val listOfUserTransaction = transactions[1]
        assertEquals(listOfUserTransaction?.size, 1)
        assertEquals(listOfUserTransaction?.get(0)?.name, "Anna")
        assertEquals(listOfUserTransaction?.get(0)?.surname, "Nowak")
        assertEquals(listOfUserTransaction?.get(0)?.customerId,"2")
        assertEquals(listOfUserTransaction?.get(0)?.totalTransactionValue, BigDecimal(20))
    }

    @Test
    fun shouldReturnMultiTransactionForSelectedUsers() {
        //given
        val input = listOf("1", "2")

        //when
        val transactions = transactionHelper.getTransaction(input)
        //then
        val listOfUserTransaction = transactions[2]
        assertEquals(listOfUserTransaction?.size, 2)
    }

    @Test
    fun shouldCalculateFeeForUser() {
        val input = listOf("2")
        val givenUserTransactionsSum = BigDecimal(20)
        val givenFeePercentage = BigDecimal(1.1)

        //when
        val transactions = transactionHelper.getTransaction(input)
        //then
        val listOfUserTransaction = transactions[1]
        assertEquals(listOfUserTransaction?.get(0)?.feeValue, (givenUserTransactionsSum * givenFeePercentage / BigDecimal(100))
            .setScale(2, RoundingMode.HALF_UP))
    }

    @Test
    fun shouldSumTransactionForUserAndCalculateFee() {
        val input = listOf("1")
        val transactionsSum = BigDecimal(110)
        val givenFeePercentage = BigDecimal(1.1)

        //when
        val transactions = transactionHelper.getTransaction(input)
        //then
        val listOfUserTransaction = transactions[1]
        assertEquals(listOfUserTransaction?.get(0)?.totalTransactionValue, (transactionsSum))
        assertEquals(listOfUserTransaction?.get(0)?.feeValue, (transactionsSum * givenFeePercentage / BigDecimal(100))
            .setScale(2, RoundingMode.HALF_UP))
    }

    @Test
    fun shouldGetTimeOfLstTransactionForUser() {
        val input = listOf("1")
        //when
        val transactions = transactionHelper.getTransaction(input)
        //then
        val listOfUserTransaction = transactions[1]
        assertEquals(LocalDateTime.of(2021, 1, 1, 20, 0), listOfUserTransaction?.get(0)?.lastTransactionDate)
    }
}
