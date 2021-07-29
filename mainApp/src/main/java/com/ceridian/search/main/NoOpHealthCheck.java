package com.ceridian.search.main;

import com.codahale.metrics.health.HealthCheck;

public class NoOpHealthCheck extends HealthCheck {

    @Override
    protected Result check() throws Exception
    {
        return Result.healthy();
    }
}
