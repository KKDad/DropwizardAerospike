package com.ceridian.search.main;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Host;
import com.aerospike.client.policy.ClientPolicy;
import com.ceridian.search.aerospike.DbException;
import com.ceridian.search.aerospike.SearchDao;
import com.ceridian.search.api.SearchRecorder;
import com.ceridian.search.configuration.SearchAnonymizerConfiguration;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SearchAnonymizerAPI extends Application<SearchAnonymizerConfiguration>
{
    private static final Logger LOG = LoggerFactory.getLogger(SearchAnonymizerAPI.class);

    public static void main(String[] args) throws Exception {
        new SearchAnonymizerAPI().run(args);
    }

    @Override
    public String getName() {
        return "SearchAnonymizer";
    }


    @Override
    public void run(SearchAnonymizerConfiguration configuration, Environment environment) throws DbException {
        final var healthCheck = new NoOpHealthCheck();
        environment.healthChecks().register("template", healthCheck);

        // === AeroSpike Database Setup and Cleanup
        var policy = new ClientPolicy();
        policy.user = configuration.aerospike.user;
        policy.password = configuration.aerospike.password;
//        policy.authMode = configuration.aerospike.authMode;
//        policy.tlsPolicy = configuration.aerospike.tlsPolicy;
//
//        configuration.aerospike.policy = policy.readPolicyDefault;
//        configuration.aerospike.writePolicy = policy.writePolicyDefault;

        Host[] hosts = Host.parseHosts(configuration.aerospike.host, configuration.aerospike.port);
        var client = new AerospikeClient(policy, hosts);
        configuration.aerospike.setServerSpecific(client);


        // === Register API Handlers
        final SearchDao searchDao = new SearchDao(client, configuration.aerospike);
        environment.jersey().register(new SearchRecorder(searchDao));
    }

}