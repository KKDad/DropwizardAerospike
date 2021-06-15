package com.ceridian.search.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AnonymizerConfiguration {
    @JsonProperty("host")
    public String host;

    @JsonProperty("port")
    public Integer port;
}
