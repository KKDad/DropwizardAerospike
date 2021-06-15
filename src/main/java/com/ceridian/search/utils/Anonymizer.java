package com.ceridian.search.utils;

import com.ceridian.search.configuration.PresidioConfiguration;
import com.ceridian.search.models.AnalyzeRequest;
import com.ceridian.search.models.AnalyzedItem;
import com.ceridian.search.models.AnonymizeRequest;
import com.ceridian.search.models.AnonymizeResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.List;

public class Anonymizer {

    private static final Logger LOG = LoggerFactory.getLogger(Anonymizer.class);
    private final PresidioConfiguration config;




    public Anonymizer(PresidioConfiguration config) {
        this.config = config;
    }

    public String anonymize(String input, String language) throws AnonymizeException
    {
        String processed;
        List<AnalyzedItem> results = doPresidoAnalysis(input, language);
        if (results.isEmpty()) {
            LOG.trace("No PII detected in input string");
            processed = input;
        } else {
            AnonymizeResult result = doPresidoAnonymize(input, results);
            processed = result.text;
        }

        return processed.replaceAll("[0-9]","#");
    }


    /**
     * Make a REST call to a Presido service to analyze a string in preparation for
     * Anonymization
     *
     * @param queryRaw String to analyze
     * @param language Language of the string under analysis
     * @return List of items identified
     * @throws AnonymizeException
     */
    private List<AnalyzedItem> doPresidoAnalysis(String queryRaw, String language) throws AnonymizeException {
        var analyzeQuery = new AnalyzeRequest(queryRaw, language);
        analyzeQuery.scoreThreshold = config.analyzer.scoreThreshold;
        Entity<AnalyzeRequest> analyzeEntity = Entity.json(analyzeQuery);

        var client = ClientBuilder.newClient();
        var analyzeResponse = client.target(
                String.format("http://%s:%d/analyze", config.analyzer.host, config.analyzer.port))
                .request()
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .post(analyzeEntity);

        if (analyzeResponse.getStatus() != 200)
            throw new AnonymizeException("Unable to analyze query");

        analyzeResponse.bufferEntity();
        return analyzeResponse.readEntity(new GenericType<>() {
        });
    }

    /**
     * Make a REST call to a presido service to anonymize a string
     *
     * @param queryRaw String to anonymize
     * @param results  Items in the string to process
     * @return String that has been anonymized
     * @throws AnonymizeException
     */
    private AnonymizeResult doPresidoAnonymize(String queryRaw, List<AnalyzedItem> results) throws AnonymizeException {
        var anonymizeQuery = new AnonymizeRequest(queryRaw, results);
        Entity<AnonymizeRequest> anonymizeEntity = Entity.json(anonymizeQuery);

        var client = ClientBuilder.newClient();
        var anonymizeResponse = client.target(
                String.format("http://%s:%d/anonymize", config.anonymizer.host, config.anonymizer.port))
                .request()
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .post(anonymizeEntity);

        if (anonymizeResponse.getStatus() != 200)
            throw new AnonymizeException("Unable to anonymize query");

        anonymizeResponse.bufferEntity();
        return anonymizeResponse.readEntity(AnonymizeResult.class);
    }

}
