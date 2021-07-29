package com.ceridian.search.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jdbi.v3.core.mapper.reflect.JdbiConstructor;

import java.beans.ConstructorProperties;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchQueryRaw {

    @JsonProperty("SearchID")
    public String searchId;

    @JsonProperty("Source")
    public SourceType source;

    @JsonProperty("TimePerformed")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    public LocalDateTime timePerformed;

    @JsonProperty("QueryTimeMS")
    public Long queryTimeMs;

    @JsonProperty("SessionID")
    public String sessionId;

    @JsonProperty("ClientID")
    public Long clientId;

    @JsonProperty("UserRole")
    public String userRole;

    @JsonProperty("Query")
    public String query;

    public SearchQueryRaw() { }

    @JdbiConstructor
    @ConstructorProperties({"searchid", "source", "timequery", "querytimems", "sessionid", "clientid", "userrole", "query"})
    @SuppressWarnings("java:S107")
    public SearchQueryRaw(String searchID, SourceType source, LocalDateTime timeQuery, Long queryTimeMS, String sessionID, long clientID, String userRole, String query) {
        this.searchId = searchID;
        this.source = source;
        this.timePerformed = timeQuery;
        this.sessionId = sessionID;
        this.clientId = clientID;
        this.userRole = userRole;
        this.query = query;
        this.queryTimeMs = queryTimeMS;
    }

    @Override
    public String toString() {
        return String.format("SearchQuery{SearchID=%s, Source=%s, TimePerformed=%s, QueryTimeMS='%d', SessionID='%s', ClientID=%d, UserRole='%s', Query='%s'}", searchId, source, timePerformed, queryTimeMs, sessionId, clientId, userRole, query);
    }

    public enum SourceType {
        INTELLIGENT_SEARCH,
        GLOBAL_SEARCH,
        DFA
    }
}
