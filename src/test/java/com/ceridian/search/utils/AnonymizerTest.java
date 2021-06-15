package com.ceridian.search.utils;

import com.ceridian.search.configuration.PresidioConfiguration;
import com.ceridian.search.configuration.SearchAnonymizerConfiguration;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.dropwizard.testing.ResourceHelpers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class AnonymizerTest {

    @Test
    void anonymizeTest() {
        try {
            // Arrange
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            mapper.findAndRegisterModules();
            PresidioConfiguration config = mapper.readValue(new File(ResourceHelpers.resourceFilePath("presidio.yml")), PresidioConfiguration.class);

            String query = "hello world, my name is Jane Doe. My number is: 034453334";
            Anonymizer subject = new Anonymizer(config);
            String result = subject.Anonymize(query, "en");


        } catch (AnonymizeException | IOException e) {
            Assert.fail(e.getLocalizedMessage());
        }
    }


}