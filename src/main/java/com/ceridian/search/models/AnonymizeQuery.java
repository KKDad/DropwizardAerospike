package com.ceridian.search.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class AnonymizeQuery {
    @JsonProperty("text")
    public String text;

    @JsonProperty("anonymizers")
    public Map<String, Anonymizer> anonymizers;

    @JsonProperty("analyzer_results")
    public List<AnalyzeResultItem> analyzerResults;
}
