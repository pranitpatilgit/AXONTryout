package com.pranit.core

import org.axonframework.commandhandling.RoutingKey
import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.UUID

class CreateProductCartCommand(
        @RoutingKey val cartId: UUID
)

data class SelectProductCommand(

        @TargetAggregateIdentifier
        val cartId : UUID,

        val productId : UUID,
        val quantity : Int
)

data class DeselectProductCommand(

        @TargetAggregateIdentifier
        val cartId : UUID,

        val productId : UUID,
        val quantity : Int
)

data class ConfirmOrderCommand(

        @TargetAggregateIdentifier
        val cartId : UUID
)