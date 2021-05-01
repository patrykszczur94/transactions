package com.ratcoding.transfer.commission

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.math.BigDecimal
import java.math.RoundingMode

internal class FeeServiceTest {
    private val mockLogRepo: LogRepository = Mockito.mock(LogRepository::class.java)
    private val testFeeService: FeeService = FeeService(InMemoryRepos.InMemoryFeeRepo(), mockLogRepo)

    @Test
    fun shouldReturnFeeAsZeroWhenAmountOutOfScopeAndSetCorrectUserId() {
        //given
        val givenCustomerId = "1"
        val outOfScopeValue = 5000
        val outOfScopeMap =  mapOf(givenCustomerId to BigDecimal(outOfScopeValue))

        //when
        val feeForGivenUserIdAndAmount = testFeeService.getFeeForGivenUserIdAndAmount(outOfScopeMap)

        //then
        assertEquals(feeForGivenUserIdAndAmount.keys.first(), givenCustomerId)
        assertEquals(feeForGivenUserIdAndAmount.values.first(), BigDecimal(0))
    }

    @Test
    fun shouldReturnFirstFeeFromListAndSetCorrectUserId() {
        //given
        val givenCustomerId = "1"
        val transactionsValues  = 1000
        val givenMap =  mapOf(givenCustomerId to BigDecimal(transactionsValues))
        val expectedFee = BigDecimal(1.1).setScale(1, RoundingMode.HALF_UP)

        //when
        val feeForGivenUserIdAndAmount = testFeeService.getFeeForGivenUserIdAndAmount(givenMap)

        //then
        assertEquals(feeForGivenUserIdAndAmount.keys.first(), givenCustomerId)
        assertEquals(feeForGivenUserIdAndAmount.values.first(), expectedFee)
    }
}


