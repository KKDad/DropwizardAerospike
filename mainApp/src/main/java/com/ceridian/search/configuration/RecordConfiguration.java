package com.ceridian.search.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RecordConfiguration {

    @JsonProperty("enabled")
    public Boolean enabled;

    @JsonProperty("namespace")
    public String namespace;
}
