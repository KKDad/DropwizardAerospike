package com.ceridian.search.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnalyzedItem {
    @JsonProperty("analysis_explanation")
    public String analysisExplanation;

    @JsonProperty("end")
    public Integer end;

    @JsonProperty("entity_type")
    public String entityType;

    @JsonProperty("score")
    public Double score;

    @JsonProperty("start")
    public Integer start;

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
