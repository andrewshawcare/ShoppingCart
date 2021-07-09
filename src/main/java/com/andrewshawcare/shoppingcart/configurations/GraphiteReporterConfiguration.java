package com.andrewshawcare.shoppingcart.configurations;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public record GraphiteReporterConfiguration(
    @NotEmpty String hostname,
    @Min(1) @Max(65535) int port
) { }
