package com.ratcoding.transfer.commission

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal

@Document("fee")
data class FeeTable(@JsonProperty("transaction_value_less_than") var value: Long,
                    @JsonProperty("fee_percentage_of_transaction_value") @JsonDeserialize(using = NumberDeserializer::class) var percentage: BigDecimal?)
