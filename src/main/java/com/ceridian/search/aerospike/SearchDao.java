package com.ceridian.search.aerospike;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.ceridian.search.configuration.AerospikeConfiguration;
import com.ceridian.search.models.SearchQuery;

import java.time.LocalDateTime;
import java.util.UUID;

public class SearchDao implements ISearchDao {

    private final AerospikeClient client;
    private final AerospikeConfiguration params;

    public SearchDao(AerospikeClient client, AerospikeConfiguration params) {
        this.client = client;
        this.params = params;
    }

    @Override
    public boolean recordQuery(SearchQuery searchQuery) {
        var key = new Key(params.namespace, params.bucket, searchQuery.searchId);
        var binSearchId = new Bin("searchId", searchQuery.searchId);
        var binSource = new Bin("source", searchQuery.source);
        var binTimePerformed = new Bin("timePerformed", searchQuery.timePerformed);
        var binQueryTimeMS = new Bin("queryTimeMs", searchQuery.queryTimeMs);
        var binSessionId = new Bin("sessionId", searchQuery.sessionId);
        var binClientId = new Bin("clientId", searchQuery.clientId);
        var binUserRole = new Bin("userRole", searchQuery.userRole);
        var binQuery = new Bin("query", searchQuery.query);

        client.put(null, key, binSearchId, binSource, binTimePerformed, binQueryTimeMS, binSessionId, binClientId, binUserRole, binQuery);

        return true;
    }

    @Override
    public SearchQuery retrieveQueryByKey(UUID id) throws DbException {
        var key = new Key(params.namespace, params.bucket, id.toString());

        Record record = client.get(null, key);
        if (record == null || record.bins.size() == 0)
            throw new DbException("Record now located");
        try {
            var searchQuery = new SearchQuery();
            searchQuery.searchId = id.toString();
            searchQuery.source = SearchQuery.SourceType.valueOf(record.bins.get("source").toString());
            searchQuery.queryTimeMs = Long.parseLong(record.bins.get("queryTimeMs").toString());
            searchQuery.query = record.bins.get("query").toString();
            searchQuery.userRole = record.bins.get("userRole").toString();
            searchQuery.timePerformed = LocalDateTime.parse(record.bins.get("timePerformed").toString());
            searchQuery.clientId = Long.parseLong(record.bins.get("clientId").toString());
            searchQuery.sessionId = record.bins.get("sessionId").toString();
            return searchQuery;
        } catch (Exception e) {
            throw new DbException(e.getLocalizedMessage());
        }
    }


}
