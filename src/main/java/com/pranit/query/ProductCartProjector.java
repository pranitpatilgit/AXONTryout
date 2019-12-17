package com.pranit.query;

import com.pranit.core.FindProductCartQuery;
import com.pranit.core.ProductCartCreatedEvent;
import com.pranit.core.ProductDeselectedEvent;
import com.pranit.core.ProductSelectedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.UUID;

@Component
public class ProductCartProjector {

    private final ProductCartViewRepository repository;

    @Autowired
    public ProductCartProjector(ProductCartViewRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void on(ProductCartCreatedEvent event){
        ProductCartView productCartView = new ProductCartView(event.getCartId(), Collections.<UUID, Integer>emptyMap());
        repository.save(productCartView);
    }

    @EventHandler
    public void on(ProductSelectedEvent event){
        repository.findById(event.getCartId()).ifPresent(
                productCartView -> productCartView.addProducts(event.getProductId(), event.getQuantity())
        );
    }

    @EventHandler
    public void on(ProductDeselectedEvent event){
        repository.findById(event.getCartId()).ifPresent(
                productCartView -> productCartView.removeProducts(event.getProductId(), event.getQuantity())
        );
    }

    @QueryHandler
    public ProductCartView handle(FindProductCartQuery query){
        return repository.findById(query.getCardId()).orElse(null);
    }
}
