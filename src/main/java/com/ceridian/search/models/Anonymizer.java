package com.ceridian.search.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

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
}
