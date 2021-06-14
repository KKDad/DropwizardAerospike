package com.ceridian.search.api;

import com.ceridian.search.configuration.SearchAnonymizerConfiguration;
import com.ceridian.search.main.SearchAnonymizerAPI;
import com.ceridian.search.models.SearchQueryAnonymized;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(DropwizardExtensionsSupport.class)
public class SearchRecorderTest
{
    private static final Logger LOG = LoggerFactory.getLogger(SearchRecorderTest.class);

    private static final DropwizardAppExtension<SearchAnonymizerConfiguration> EXT = new DropwizardAppExtension<>(
            SearchAnonymizerAPI.class,
            ResourceHelpers.resourceFilePath("test.yml")
    );

    @Test
    void createUpdateSystemTemplate() {
        Client client = EXT.client();

        SearchQueryAnonymized query = new SearchQueryAnonymized();
        query.query = "Time away from Work";
        query.queryTimeMs = 40L;
        query.sessionId = UUID.randomUUID().toString();
        query.clientId = 10000L;
        query.timePerformed = LocalDateTime.of(2021, 02, 15, 1, 15, 00);
        query.source = SearchQueryAnonymized.SourceType.INTELLIGENT_SEARCH;
        query.userRole = "ADMIN";

        Entity<SearchQueryAnonymized> entity = Entity.json(query);
        Response response = client.target(
                String.format("http://localhost:%d/api/v1/query/record", EXT.getLocalPort()))
                .request()
                .post(entity);
        response.bufferEntity();

        // Assert that we successfully recorded the query
        assertEquals(201, response.getStatus());
        UUID queryId = UUID.fromString(response.getLocation().getPath().replace("/api/v1/query/record/", ""));

        response = client.target(
                String.format("http://localhost:%d/api/v1/query/record/%s", EXT.getLocalPort(), queryId))
                .request()
                .get();
        assertEquals(200, response.getStatus());
        try {
            ObjectMapper mapper  = getObjectMapper();
            query.searchId = queryId.toString();
            JsonNode actualResponse = mapper.readTree(response.readEntity(String.class));
            JsonNode expectedResponse = mapper.readTree(mapper.writeValueAsString(query));

            assertThat(actualResponse, is(expectedResponse));
        } catch (IOException e) {
            fail();
        }
        ObjectMapper mapper = getObjectMapper();

    }


    private ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }
}
