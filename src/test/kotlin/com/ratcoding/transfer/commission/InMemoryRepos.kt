package com.ratcoding.transfer.commission

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import org.slf4j.LoggerFactory
import org.springframework.data.annotation.Id
import org.springframework.data.domain.Example
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

class InMemoryRepos {

    class InMemoryTransactionRepo: TransactionRepository {
        val transactions = listOf(
          Transaction("buuu123", BigDecimal(10), "Jan", "Kowalski", "1", LocalDateTime.of(2020, 1, 1, 19, 0)),
          Transaction("buuu234", BigDecimal(20), "Anna", "Nowak", "2", LocalDateTime.now()),
          Transaction("buuu345", BigDecimal(30), "Roman", "Lee", "3", LocalDateTime.now()),
          Transaction("buuu456", BigDecimal(40), "Waldemar", "Foo", "4", LocalDateTime.now()),
          Transaction("buuu567", BigDecimal(50), "Jacek", "Bee", "5", LocalDateTime.now()),
          Transaction("0000000", BigDecimal(100), "Jan", "Kowalski", "1", LocalDateTime.of(2021, 1, 1, 20, 0)),
        )


        override fun findAllByCustomerIdIn(customerId: List<String>): List<Transaction> {
            val foundedTransactions = ArrayList<List<Transaction>>()
            customerId.forEach { x ->
                run {
                    val element = transactions.filter { it.customerId == x }
                    foundedTransactions.add(element)
                }
            }
            return foundedTransactions.flatMap {it.toList()}
        }

        override fun <S : Transaction?> save(entity: S): S {
            TODO("Not yet implemented")
        }

        override fun <S : Transaction?> saveAll(entities: MutableIterable<S>): MutableList<S> {
            TODO("Not yet implemented")
        }

        override fun findById(id: String): Optional<Transaction> {
            TODO("Not yet implemented")
        }

        override fun existsById(id: String): Boolean {
            TODO("Not yet implemented")
        }

        override fun findAll(): MutableList<Transaction> {
            return transactions.toMutableList()
        }

        override fun findAll(sort: Sort): MutableList<Transaction> {
            TODO("Not yet implemented")
        }

        override fun <S : Transaction?> findAll(example: Example<S>): MutableList<S> {
            TODO("Not yet implemented")
        }

        override fun <S : Transaction?> findAll(example: Example<S>, sort: Sort): MutableList<S> {
            TODO("Not yet implemented")
        }

        override fun findAll(pageable: Pageable): Page<Transaction> {
            TODO("Not yet implemented")
        }

        override fun <S : Transaction?> findAll(example: Example<S>, pageable: Pageable): Page<S> {
            TODO("Not yet implemented")
        }

        override fun findAllById(ids: MutableIterable<String>): MutableIterable<Transaction> {
            TODO("Not yet implemented")
        }

        override fun count(): Long {
            TODO("Not yet implemented")
        }

        override fun <S : Transaction?> count(example: Example<S>): Long {
            TODO("Not yet implemented")
        }

        override fun deleteById(id: String) {
            TODO("Not yet implemented")
        }

        override fun delete(entity: Transaction) {
            TODO("Not yet implemented")
        }

        override fun deleteAll(entities: MutableIterable<Transaction>) {
            TODO("Not yet implemented")
        }

        override fun deleteAll() {
            TODO("Not yet implemented")
        }

        override fun <S : Transaction?> findOne(example: Example<S>): Optional<S> {
            TODO("Not yet implemented")
        }

        override fun <S : Transaction?> exists(example: Example<S>): Boolean {
            TODO("Not yet implemented")
        }

        override fun <S : Transaction?> insert(entity: S): S {
            TODO("Not yet implemented")
        }

        override fun <S : Transaction?> insert(entities: MutableIterable<S>): MutableList<S> {
            TODO("Not yet implemented")
        }
    }

    class InMemoryFeeRepo: FeeRepository {
        override fun findSortedFeesForGivenAmount(value: Long): List<FeeTable> {
            if (value == 5000L) {
                return emptyList()
            }
            return listOf(
                FeeTable(1000L, BigDecimal(1.1).setScale(1, RoundingMode.HALF_UP)),
                FeeTable(2000L, BigDecimal(2.1).setScale(1, RoundingMode.HALF_UP)),
                FeeTable(3000L, BigDecimal(3.0).setScale(1, RoundingMode.HALF_UP)),
                FeeTable(4000L, BigDecimal(0.1).setScale(1, RoundingMode.HALF_UP))
            )
        }

        override fun <S : FeeTable?> save(entity: S): S {
            TODO("Not yet implemented")
        }

        override fun <S : FeeTable?> saveAll(entities: MutableIterable<S>): MutableList<S> {
            TODO("Not yet implemented")
        }

        override fun findById(id: String): Optional<FeeTable> {
            TODO("Not yet implemented")
        }

        override fun existsById(id: String): Boolean {
            TODO("Not yet implemented")
        }

        override fun findAll(): MutableList<FeeTable> {
            TODO("Not yet implemented")
        }

        override fun findAll(sort: Sort): MutableList<FeeTable> {
            TODO("Not yet implemented")
        }

        override fun <S : FeeTable?> findAll(example: Example<S>): MutableList<S> {
            TODO("Not yet implemented")
        }

        override fun <S : FeeTable?> findAll(example: Example<S>, sort: Sort): MutableList<S> {
            TODO("Not yet implemented")
        }

        override fun findAll(pageable: Pageable): Page<FeeTable> {
            TODO("Not yet implemented")
        }

        override fun <S : FeeTable?> findAll(example: Example<S>, pageable: Pageable): Page<S> {
            TODO("Not yet implemented")
        }

        override fun findAllById(ids: MutableIterable<String>): MutableIterable<FeeTable> {
            TODO("Not yet implemented")
        }

        override fun count(): Long {
            TODO("Not yet implemented")
        }

        override fun <S : FeeTable?> count(example: Example<S>): Long {
            TODO("Not yet implemented")
        }

        override fun deleteById(id: String) {
            TODO("Not yet implemented")
        }

        override fun delete(entity: FeeTable) {
            TODO("Not yet implemented")
        }

        override fun deleteAll(entities: MutableIterable<FeeTable>) {
            TODO("Not yet implemented")
        }

        override fun deleteAll() {
            TODO("Not yet implemented")
        }

        override fun <S : FeeTable?> findOne(example: Example<S>): Optional<S> {
            TODO("Not yet implemented")
        }

        override fun <S : FeeTable?> exists(example: Example<S>): Boolean {
            TODO("Not yet implemented")
        }

        override fun <S : FeeTable?> insert(entity: S): S {
            TODO("Not yet implemented")
        }

        override fun <S : FeeTable?> insert(entities: MutableIterable<S>): MutableList<S> {
            TODO("Not yet implemented")
        }
    }
}
