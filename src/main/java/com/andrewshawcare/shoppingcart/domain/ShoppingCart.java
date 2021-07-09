package com.andrewshawcare.shoppingcart.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;

public class ShoppingCart {
    private final Collection<Product> products = new ArrayList<>();
    private final int scale = 2;
    private final RoundingMode roundingMode = RoundingMode.HALF_UP;

    public void addProduct(Product product) {
        products.add(product);
    }

    public Collection<Product> getProducts() {
        return products;
    }

    public BigDecimal totalSalesTaxAmount(BigDecimal salesTaxRate) {
        return products.stream().map(Product::unitPrice).reduce(
            BigDecimal.ZERO,
            (sum, unitPrice) -> sum.add(unitPrice.multiply(salesTaxRate))
        ).setScale(scale, roundingMode);
    }

    public BigDecimal totalPrice() {
        return products.stream().map(Product::unitPrice).reduce(BigDecimal.ZERO, BigDecimal::add).setScale(scale, roundingMode);
    }

    public BigDecimal totalPrice(BigDecimal salesTaxRate) {
        return totalPrice().add(totalSalesTaxAmount(salesTaxRate)).setScale(scale, roundingMode);
    }
}
