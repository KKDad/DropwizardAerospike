package com.ceridian.search.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnalyzeRequest {

   public AnalyzeRequest(String queryRaw, String language) {
        this.text = queryRaw;
        this.language = language;
    }

    @JsonProperty("text")
    public String text;

    @JsonProperty("language")
    public String language;

    @JsonProperty("score_threshold")
    public Double scoreThreshold;

}
