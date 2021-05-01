package com.ratcoding.transfer.commission

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class TransactionController(private val transactionFacade: TransactionFacade) {

    @GetMapping("/ids")
    fun getTransactionsForCustomers(@RequestParam(defaultValue = "") ids: List<String>): ResponseEntity<Map<Int, List<UserResponseEntity>>> {
        return try {
            ResponseEntity.ok(transactionFacade.getTransaction(ids))
        } catch (e: TransactionException) {
            ResponseEntity.badRequest().build()
        }
    }
}
