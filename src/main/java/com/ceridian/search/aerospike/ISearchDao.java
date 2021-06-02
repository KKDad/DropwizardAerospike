package com.ceridian.search.aerospike;

import com.ceridian.search.models.SearchQuery;

import java.util.UUID;

public interface ISearchDao {
    boolean recordQuery(SearchQuery searchQuery);

    SearchQuery retrieveQueryByKey(UUID id) throws DbException;
}
