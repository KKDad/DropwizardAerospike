package com.ceridian.search.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnonymizeResult {
    @JsonProperty("text")
    public String text;

    @JsonProperty("items")
    public List<AnonymizedItem> items;

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
