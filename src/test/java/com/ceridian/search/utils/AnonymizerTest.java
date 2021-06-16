package com.ceridian.search.utils;

import com.ceridian.search.configuration.PresidioConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.dropwizard.testing.ResourceHelpers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.io.IOException;

class AnonymizerTest {

    @ParameterizedTest(name = "anonymizeTest_{index}")
    @ValueSource(strings = {
            "hello world, my name is Jane Doe. My number is: 034453334/en/hello world, my name is <PERSON>. My number is: #########",
            "What is Dylan's home address?/en/What is <PERSON>'s home address?",
            "TAWF/en/<PERSON>",
            "Vacation Schedule/en/Vacation Schedule",
            "Book time off/en/Book time off",
            "Book sunday off/en/Book <DATE_TIME> off",
            "When is my next pay cheque?/en/When is my next pay cheque?",
            "Trade my shift on Sunday to Natan/en/Trade my shift on <DATE_TIME> to <PERSON>",
            "All of the people who report to Lauris/en/All of the people who report to <LOCATION>"
    })
    void anonymizeTest(String input) throws AnonymizeException, IOException {
        String[] i = input.split("/");
        String query = i[0];
        String language = i[1];
        String result = i[2];

        // Arrange
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();
        PresidioConfiguration config = mapper.readValue(new File(ResourceHelpers.resourceFilePath("presidio.yml")), PresidioConfiguration.class);

        Anonymizer subject = new Anonymizer(config);
        String actualResult = subject.anonymize(query, language);

        Assert.assertEquals(result, actualResult);

    }


}