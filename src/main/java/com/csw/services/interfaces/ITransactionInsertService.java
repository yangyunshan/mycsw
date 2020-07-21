package com.csw.services.interfaces;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

public interface ITransactionInsertService {
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path(value = "/TransactionInsert")
    String transactionInsertResponse(@QueryParam("type") String type,
                                     @QueryParam("owner") String owner,
                                     String data);
}
