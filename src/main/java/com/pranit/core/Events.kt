package com.pranit.core

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.UUID

data class ProductCartCreatedEvent(
        val cartId: UUID
)

data class ProductSelectedEvent(
        val cartId : UUID,
        val productId : UUID,
        val quantity : Int
)

data class ProductDeselectedEvent(
        val cartId : UUID,
        val productId : UUID,
        val quantity : Int
)

data class OrderConfirmedEvent(
        val cartId : UUID
)