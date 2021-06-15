package com.ceridian.search.aerospike;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.ceridian.search.configuration.AerospikeConfiguration;
import com.ceridian.search.models.SearchQueryAnonymized;

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
    public boolean recordQuery(SearchQueryAnonymized searchQuery) {
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
    public SearchQueryAnonymized retrieveQueryByKey(UUID id) throws DbException {
        var key = new Key(params.namespace, params.bucket, id.toString());

        var aeroSpikeRecord = client.get(null, key);
        if (aeroSpikeRecord == null || aeroSpikeRecord.bins.size() == 0)
            throw new DbException("Record now located");
        try {
            var searchQuery = new SearchQueryAnonymized();
            searchQuery.searchId = id.toString();
            searchQuery.source = SearchQueryAnonymized.SourceType.valueOf(aeroSpikeRecord.bins.get("source").toString());
            searchQuery.queryTimeMs = Long.parseLong(aeroSpikeRecord.bins.get("queryTimeMs").toString());
            searchQuery.query = aeroSpikeRecord.bins.get("query").toString();
            searchQuery.userRole = aeroSpikeRecord.bins.get("userRole").toString();
            searchQuery.timePerformed = LocalDateTime.parse(aeroSpikeRecord.bins.get("timePerformed").toString());
            searchQuery.clientId = Long.parseLong(aeroSpikeRecord.bins.get("clientId").toString());
            searchQuery.sessionId = aeroSpikeRecord.bins.get("sessionId").toString();
            return searchQuery;
        } catch (Exception e) {
            throw new DbException(e.getLocalizedMessage());
        }
    }


}
