package com.andrewshawcare.shoppingcart.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.stream.Collectors;

public class ShoppingCartTests {
    @Test
    void addProductsToTheShoppingCart() {
        // Given:
        //   * An empty shopping cart
        var shoppingCart = new ShoppingCart();
        //   * And a product, Dove Soap with a unit price of 39.99
        var doveSoapProduct = new Product("Dove Soap", BigDecimal.valueOf(39.99));

        // When:
        //   * The user adds 5 Dove Soaps to the shopping cart
        shoppingCart.addProduct(doveSoapProduct);
        shoppingCart.addProduct(doveSoapProduct);
        shoppingCart.addProduct(doveSoapProduct);
        shoppingCart.addProduct(doveSoapProduct);
        shoppingCart.addProduct(doveSoapProduct);

        // Then:
        var products = shoppingCart.getProducts();
        //   * The shopping cart should contain 5 Dove Soaps each with a unit price of 39.99
        Assertions.assertEquals(5, products.size());
        products.forEach(product -> {
            Assertions.assertEquals("Dove Soap", product.name());
            Assertions.assertEquals(BigDecimal.valueOf(39.99), product.unitPrice());
        });
        //   * And the shopping cart's total price should equal 199.95
        Assertions.assertEquals(BigDecimal.valueOf(199.95), shoppingCart.totalPrice());
    }

    @Test
    void addAdditionalProductsOfTheSameTypeToTheShoppingCart() {
        // Given:
        //   * An empty shopping cart
        var shoppingCart = new ShoppingCart();
        //   * And a product, Dove Soap with a unit price of 39.99
        var doveSoapProduct = new Product("Dove Soap", BigDecimal.valueOf(39.99));

        // When:
        //   * The user adds 5 Dove Soaps to the shopping cart
        shoppingCart.addProduct(doveSoapProduct);
        shoppingCart.addProduct(doveSoapProduct);
        shoppingCart.addProduct(doveSoapProduct);
        shoppingCart.addProduct(doveSoapProduct);
        shoppingCart.addProduct(doveSoapProduct);
        //   * And then adds another 3 Dove Soaps to the shopping cart
        shoppingCart.addProduct(doveSoapProduct);
        shoppingCart.addProduct(doveSoapProduct);
        shoppingCart.addProduct(doveSoapProduct);

        // Then:
        var products = shoppingCart.getProducts();
        //   * The shopping cart should contain 8 Dove Soaps each with a unit price of 39.99
        Assertions.assertEquals(8, products.size());
        products.forEach(product -> {
            Assertions.assertEquals("Dove Soap", product.name());
            Assertions.assertEquals(BigDecimal.valueOf(39.99), product.unitPrice());
        });
        //   * And the shopping cart's total price should equal 199.95
        Assertions.assertEquals(BigDecimal.valueOf(319.92), shoppingCart.totalPrice());
    }

    @Test
    void calculateTheTaxRateOfTheShoppingCartWithMultipleItems() {
        // Given:
        //   * An empty shopping cart
        var shoppingCart = new ShoppingCart();
        //   * And a product, Dove Soap with a unit price of 39.99
        var doveSoapProduct = new Product("Dove Soap", BigDecimal.valueOf(39.99));
        //   * And another product, Axe Deo with a unit price of 99.99
        var axeDeoProduct = new Product("Axe Deo", BigDecimal.valueOf(99.99));
        //   * And a sales tax rate of 12.5%
        var salesTaxRate = BigDecimal.valueOf(0.125);

        // When:
        //   * The user adds 2 Dove Soaps to the shopping cart
        shoppingCart.addProduct(doveSoapProduct);
        shoppingCart.addProduct(doveSoapProduct);
        //   * And then adds 2 Axe Deos to the shopping cart
        shoppingCart.addProduct(axeDeoProduct);
        shoppingCart.addProduct(axeDeoProduct);

        // Then:

        var products = shoppingCart.getProducts();
        //   * The shopping cart should contain 2 Dove Soaps each with a unit price of 39.99
        var doveSoapProducts = products.stream().filter(product -> product.name().equals("Dove Soap")).toList();
        Assertions.assertEquals(2, doveSoapProducts.size());
        doveSoapProducts.forEach(product -> {
            Assertions.assertEquals(BigDecimal.valueOf(39.99), product.unitPrice());
        });
        //   * And the shopping cart should contain 2 Axe Deos each with a unit price of 99.99
        var axeDeoProducts = products.stream().filter(product -> product.name().equals("Axe Deo")).toList();
        Assertions.assertEquals(2, axeDeoProducts.size());
        axeDeoProducts.forEach(product -> {
            Assertions.assertEquals(BigDecimal.valueOf(99.99), product.unitPrice());
        });
        //   * And the total sales tax amount for the shopping cart should equal 35.00
        Assertions.assertEquals(new BigDecimal("35.00"), shoppingCart.totalSalesTaxAmount(salesTaxRate));
        //   * And the shopping cart’s total price should equal 314.96
        Assertions.assertEquals(BigDecimal.valueOf(314.96), shoppingCart.totalPrice(salesTaxRate));
    }
}
