package com.ratcoding.transfer.commission

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.io.IOException
import java.math.BigDecimal
import java.time.LocalDateTime


@Document("transaction")
data class Transaction(@JsonProperty("transaction_id") @Id val id: String,
                       @JsonProperty("transaction_amount") @JsonDeserialize(using = NumberDeserializer::class) var transactionAmount: BigDecimal,
                       @JsonProperty("customer_first_name") var name: String,
                       @JsonProperty("customer_last_name") var surname: String,
                       @JsonProperty("customer_id") val customerId: String,
                       @JsonProperty("transaction_date") @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss") var transactionDate: LocalDateTime)

class NumberDeserializer: JsonDeserializer<BigDecimal>() {
    @Throws(IOException::class, JsonProcessingException::class)
    override fun deserialize(parser: JsonParser, context: DeserializationContext?): BigDecimal {
        val numberString: String = parser.text
        if (numberString.contains(",")) {
            val numberStringWithoutComma = numberString.replace(",", ".")
            return BigDecimal(numberStringWithoutComma)
        }
        return BigDecimal(numberString)
    }
}
