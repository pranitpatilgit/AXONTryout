package com.pranit.command;

import com.pranit.core.*;
import com.pranit.core.exception.ProductDeselectException;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Aggregate
public class ProductCart {

    private static final Logger logger = LoggerFactory.getLogger(ProductCart.class);

    @AggregateIdentifier
    private UUID cartId;

    private Map<UUID, Integer> selectedProducts;

    public ProductCart() {
    }

    @CommandHandler
    public ProductCart(CreateProductCartCommand createProductCartCommand) {
        logger.debug("Publishing ProductCartCreatedEvent for - {}", createProductCartCommand.getCartId());
        AggregateLifecycle.apply(new ProductCartCreatedEvent(createProductCartCommand.getCartId()));
    }

    @CommandHandler
    public void handle(SelectProductCommand selectProductCommand) {
        logger.debug("Publishing ProductSelectedEvent for - {}", cartId);
        AggregateLifecycle.apply(new ProductSelectedEvent(cartId, selectProductCommand.getProductId(),
                selectProductCommand.getQuantity()));
    }

    @CommandHandler
    public void handle(DeselectProductCommand deselectProductCommand) {
        if (!selectedProducts.containsKey(deselectProductCommand.getProductId())
                || selectedProducts.get(deselectProductCommand.getProductId()) - deselectProductCommand.getQuantity() < 0) {
            throw new ProductDeselectException("Cannot deselect product, either product not in cart or quantity to deselect is more.");
        }

        logger.debug("Publishing ProductDeselectedEvent for - {}", cartId);
        AggregateLifecycle.apply(new ProductDeselectedEvent(cartId, deselectProductCommand.getProductId(),
                deselectProductCommand.getQuantity()));
    }

    @CommandHandler
    public void handle(ConfirmOrderCommand confirmOrderCommand){
        logger.debug("Publishing OrderConfirmedEvent for - {}", cartId);
        AggregateLifecycle.apply(new OrderConfirmedEvent(cartId));
    }

    @EventSourcingHandler
    public void on(ProductCartCreatedEvent event) {
        selectedProducts = new HashMap<>();
        cartId = event.getCartId();
    }

    @EventSourcingHandler
    public void on(ProductSelectedEvent event){
        selectedProducts.merge(event.getProductId(), event.getQuantity(), Integer::sum);
    }

}
