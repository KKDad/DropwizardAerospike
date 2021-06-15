package com.ceridian.search.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

public class SearchAnonymizerConfiguration extends Configuration {

    @JsonProperty("aerospike")
    public AerospikeConfiguration aerospike;

    @JsonProperty("presidio")
    public PresidioConfiguration presido;
}
