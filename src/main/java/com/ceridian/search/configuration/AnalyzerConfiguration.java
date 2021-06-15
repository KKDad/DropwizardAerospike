package com.ceridian.search.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AnalyzerConfiguration {
    @JsonProperty("host")
    public String host;

    @JsonProperty("port")
    public Integer port;

    @JsonProperty("score_threshold")
    public Double scoreThreshold;
}
