package com.ceridian.search.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PresidioConfiguration {

    @JsonProperty("analyzer")
    public PresidioAnalyzerConfiguration analyzer;

    @JsonProperty("anonymizer")
    public PresidioAnonymizerConfiguration anonymizer;
}
