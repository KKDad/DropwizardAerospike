package com.ceridian.search.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Anonymizer {

    @JsonProperty("type")
    public String type;

    @JsonProperty("new_value")
    public String newValue;

    @JsonProperty("masking_char")
    public String maskingChar;

    @JsonProperty("chars_to_mask")
    public Integer charsToMask;

    @JsonProperty("from_end")
    public Boolean fromEnd;

    @JsonProperty("operator")
    public String operator;

    @JsonProperty("text")
    public String text;

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
