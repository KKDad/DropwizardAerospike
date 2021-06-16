package com.ceridian.search.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PresidioAnonymizerConfiguration {
    @JsonProperty("host")
    public String host;

    @JsonProperty("port")
    public Integer port;
}
