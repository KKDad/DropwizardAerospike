package com.ceridian.search.aerospike;

import com.ceridian.search.models.SearchQueryAnonymized;

import java.util.UUID;

public interface ISearchDao {
    boolean recordQuery(SearchQueryAnonymized searchQuery);

    SearchQueryAnonymized retrieveQueryByKey(UUID id) throws DbException;
}
