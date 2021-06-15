package com.ceridian.search.utils;

import com.ceridian.search.configuration.PresidioConfiguration;
import com.ceridian.search.models.AnalyzeQuery;
import com.ceridian.search.models.AnalyzeResultItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.List;

public class Anonymizer {

    private static final Logger LOG = LoggerFactory.getLogger(Anonymizer.class);
    private final PresidioConfiguration config;


    public Anonymizer(PresidioConfiguration config) {
        this.config = config;
    }

    public String Anonymize(String queryRaw, String language) throws AnonymizeException {
        var query = new AnalyzeQuery(queryRaw, language);
        Entity<AnalyzeQuery> entity = Entity.json(query);

        var client = ClientBuilder.newClient();
        Response response = client.target(
                String.format("http://%s:%d/analyze", config.analyzer.host, config.analyzer.port))
                .request()
                .post(entity);
        response.bufferEntity();

        if (response.getStatus() != 200)
            throw new AnonymizeException("Unable to AnalyzeQuery");


        List<AnalyzeResultItem> results = response.readEntity(new GenericType<>() {});

        return "";
    }

}
