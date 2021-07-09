package com.andrewshawcare.shoppingcart.configurations;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class ShoppingCartConfiguration extends Configuration {
    @Valid
    @NotNull
    private final GraphiteReporterConfiguration graphiteReporterConfiguration;

    public ShoppingCartConfiguration(
        @JsonProperty("graphiteReporter")
        GraphiteReporterConfiguration graphiteReporterConfiguration
    ) {
        this.graphiteReporterConfiguration = graphiteReporterConfiguration;
    }

    public GraphiteReporterConfiguration graphiteReporterConfiguration() {
        return graphiteReporterConfiguration;
    }
}
