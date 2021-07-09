package com.andrewshawcare.shoppingcart;

import com.andrewshawcare.shoppingcart.configurations.ShoppingCartConfiguration;
import com.andrewshawcare.shoppingcart.domain.ShoppingCart;
import com.andrewshawcare.shoppingcart.resources.ShoppingCartResource;
import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;
import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

public class ShoppingCartApplication extends Application<ShoppingCartConfiguration> {
    public static void main(String[] args) throws Exception {
        new ShoppingCartApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<ShoppingCartConfiguration> bootstrap) {
        bootstrap.setConfigurationSourceProvider(new SubstitutingSourceProvider(
            bootstrap.getConfigurationSourceProvider(),
            new EnvironmentVariableSubstitutor(true)
        ));
    }

    @Override
    public void run(ShoppingCartConfiguration shoppingCartConfiguration, Environment environment) throws Exception {
        var graphiteReporterConfiguration = shoppingCartConfiguration.graphiteReporterConfiguration();
        var graphite = new Graphite(new InetSocketAddress(
            graphiteReporterConfiguration.hostname(),
            graphiteReporterConfiguration.port()
        ));
        var graphiteReporter = GraphiteReporter
            .forRegistry(environment.metrics())
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.MILLISECONDS)
            .filter(MetricFilter.ALL)
            .build(graphite);
        graphiteReporter.start(1, TimeUnit.MINUTES);

        environment.jersey().register(new ShoppingCartResource(new ShoppingCart()));
    }
}
