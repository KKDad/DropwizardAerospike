package com.ceridian.search.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchRecorderConfiguration {

    @JsonProperty("anonyimized")
    public RecordConfiguration anonyimized;

    @JsonProperty("raw")
    public RecordConfiguration raw;
}
