package com.ceridian.search.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnalyzeQuery {

    public  AnalyzeQuery() {
        // No-Op for json Serialization
    }

    public AnalyzeQuery(String queryRaw, String language) {
        this.text = queryRaw;
        this.language = language;
    }

    @JsonProperty("text")
    public String text;
    @JsonProperty("language")
    public String language;

}
