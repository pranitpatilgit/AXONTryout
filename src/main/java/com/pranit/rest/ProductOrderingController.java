package com.pranit.rest;

import com.pranit.core.CreateProductCartCommand;
import com.pranit.core.DeselectProductCommand;
import com.pranit.core.FindProductCartQuery;
import com.pranit.core.SelectProductCommand;
import com.pranit.core.exception.ProductExecption;
import com.pranit.query.ProductCartView;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/productCart")
public class ProductOrderingController {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    @Autowired
    public ProductOrderingController(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create", produces = "application/json")
    public String createOrder() {
        UUID cartId = UUID.randomUUID();
        commandGateway.send(new CreateProductCartCommand(cartId));
        return cartId.toString();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{cartId}", produces = "application/json")
    public ProductCartView getProductCart(@PathVariable String cartId) {
        try {
            return queryGateway.query(new FindProductCartQuery(UUID.fromString(cartId)),
                    ResponseTypes.instanceOf(ProductCartView.class)).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new ProductExecption("Product Not Found");
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{cartId}/select/{productId}/{quantity}")
    public void selectProduct(@PathVariable String cartId,
                              @PathVariable String productId,
                              @PathVariable int quantity) {
        commandGateway.send(new SelectProductCommand(UUID.fromString(cartId), UUID.fromString(productId), quantity));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{cartId}/deselect/{productId}/{quantity}")
    public void deselectProduct(@PathVariable String cartId,
                                @PathVariable String productId,
                                @PathVariable int quantity) {
        commandGateway.send(new DeselectProductCommand(UUID.fromString(cartId), UUID.fromString(productId), quantity));
    }
}
