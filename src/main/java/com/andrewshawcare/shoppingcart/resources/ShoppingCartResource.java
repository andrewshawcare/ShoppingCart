package com.andrewshawcare.shoppingcart.resources;

import com.andrewshawcare.shoppingcart.domain.Product;
import com.andrewshawcare.shoppingcart.domain.ShoppingCart;
import com.andrewshawcare.shoppingcart.representations.ProductRepresentation;
import com.andrewshawcare.shoppingcart.representations.ShoppingCartRepresentation;
import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.util.ArrayList;

@Path("/shopping-cart")
@Produces(MediaType.APPLICATION_JSON)
public class ShoppingCartResource {
    private final ShoppingCart shoppingCart;

    public ShoppingCartResource(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    @GET
    @Timed
    public ShoppingCartRepresentation shoppingCart() {
        shoppingCart.addProduct(new Product("Dove Soap", BigDecimal.valueOf(39.99)));
        return new ShoppingCartRepresentation(
            shoppingCart.getProducts().stream().map(product -> new ProductRepresentation(product.name(), product.unitPrice())).toList(),
            shoppingCart.totalSalesTaxAmount(BigDecimal.ZERO),
            shoppingCart.totalPrice()
        );
    }
}
