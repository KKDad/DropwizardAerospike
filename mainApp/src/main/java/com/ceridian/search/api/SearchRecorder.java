package com.ceridian.search.api;

import com.ceridian.search.aerospike.DbException;
import com.ceridian.search.aerospike.ISearchDao;
import com.ceridian.search.models.SearchQueryAnonymized;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.UUID;

@Path("/api/v1/query")
public class SearchRecorder {

    private static final Logger LOG = LoggerFactory.getLogger(SearchRecorder.class);

    private final ISearchDao searchDao;

    public SearchRecorder(ISearchDao searchDao) {
        this.searchDao = searchDao;
    }

    @GET
    @Timed
    @Path("/record/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public SearchQueryAnonymized getQueryById(@NotNull @PathParam("id") UUID id) {
        try {
            return searchDao.retrieveQueryByKey(id);
        } catch (DbException e) {
            LOG.error(e.getLocalizedMessage(), e);
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

    @POST
    @Path("/record/")
    @Timed
    public Response recordQuery(SearchQueryAnonymized query, @Context UriInfo uriInfo) {
        try {
            query.searchId = UUID.randomUUID().toString();
            if (searchDao.recordQuery(query)) {
                UriBuilder builder = uriInfo.getAbsolutePathBuilder();
                builder.path(query.searchId);
                return Response.created(builder.build()).build();
            }
        } catch (Exception e) {
            LOG.error(e.getLocalizedMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.status(Response.Status.CONFLICT).build();
    }
}
