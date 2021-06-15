package com.ceridian.search.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PresidioConfiguration {

    @JsonProperty("analyzer")
    public AnalyzerConfiguration analyzer;

    @JsonProperty("anonymizer")
    public  AnonymizerConfiguration anonymizer;
}
