package com.ceridian.search.configuration;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Info;
import com.aerospike.client.policy.AuthMode;
import com.aerospike.client.policy.Policy;
import com.aerospike.client.policy.TlsPolicy;
import com.aerospike.client.policy.WritePolicy;
import com.ceridian.search.aerospike.DbException;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * Configuration data.
 */
public class AerospikeConfiguration {
    @JsonProperty("host")
    public String host;

    @JsonProperty("port")
    public Integer port;

    @JsonProperty("user")
    public String user;

    @JsonProperty("password")
    public String password;

    @JsonProperty("namespace")
    public String namespace;

    @JsonProperty("bucket")
    public String bucket;

//    public AuthMode authMode;
//    public TlsPolicy tlsPolicy;
//    public WritePolicy writePolicy;
//    public Policy policy;

    boolean singleBin;
    boolean hasGeo;
    boolean hasUdf;
    boolean hasCDTList;
    boolean hasCDTMap;

    public AerospikeConfiguration() {}

    /**
     * Some database calls need to know how the server is configured.
     */
    public void setServerSpecific(AerospikeClient client) throws DbException {
        var node = client.getNodes()[0];
        var featuresFilter = "features";
        var namespaceFilter = "namespace/" + namespace;
        Map<String, String> tokens = Info.request(null, node, featuresFilter, namespaceFilter);

        String features = tokens.get(featuresFilter);
        hasGeo = false;
        hasUdf = false;
        hasCDTList = false;
        hasCDTMap = false;

        if (features != null) {
            String[] list = features.split(";");

            for (String s : list) {
                switch (s) {
                    case "geo":
                        hasGeo = true;
                        break;
                    case "udf":
                        hasUdf = true;
                        break;
                    case "cdt-list":
                        hasCDTList = true;
                        break;
                    case "cdt-map":
                        hasCDTMap = true;
                        break;
                }
            }
        }

        String namespaceTokens = tokens.get(namespaceFilter);
        if (namespaceTokens == null) {
            throw new DbException(String.format("Failed to get namespace info: host=%s port=%d namespace=%s", host, port, namespace));
        }
        singleBin = parseBoolean(namespaceTokens, "single-bin");
    }

    private static boolean parseBoolean(String namespaceTokens, String name) {
        String search = name + '=';
        int begin = namespaceTokens.indexOf(search);
        if (begin < 0)
            return false;

        begin += search.length();
        int end = namespaceTokens.indexOf(';', begin);
        if (end < 0)
            end = namespaceTokens.length();

        var value = namespaceTokens.substring(begin, end);
        return Boolean.parseBoolean(value);
    }

    @Override
    public String toString() {
        return String.format("Parameters: host=%s port=%d ns=%s set=%s single-bin=%s", host, port, namespace, "set", singleBin);
    }
}