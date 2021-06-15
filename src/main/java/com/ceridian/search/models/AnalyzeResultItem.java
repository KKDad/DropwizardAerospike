package com.ceridian.search.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnalyzeResultItem {
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

}
