package com.ceridian.search.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnonymizeRequest {
    @JsonProperty("text")
    public String text;

    @JsonProperty("anonymizers")
    public Map<String, Anonymizer> anonymizers;

    @JsonProperty("analyzer_results")
    public List<AnalyzedItem> analyzerResults;

    public AnonymizeRequest(String queryRaw, List<AnalyzedItem> results) {
        this.text = queryRaw;
        this.analyzerResults = results;
    }

    @Override
    public String toString() {
        try {
            var mapper = new ObjectMapper();
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException ignored) {
            // NoOp
        }
        return super.toString();
    }
}
