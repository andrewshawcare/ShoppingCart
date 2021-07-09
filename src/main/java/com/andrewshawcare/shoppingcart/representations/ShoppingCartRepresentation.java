package com.andrewshawcare.shoppingcart.representations;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;
import java.util.Collection;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonSerialize
public record ShoppingCartRepresentation(
    Collection<ProductRepresentation> products,
    BigDecimal totalSalesTaxAmount,
    BigDecimal totalPrice
) { }
