package com.csw.services.interfaces;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

public interface ITransactionUpdateService {
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path(value = "/TransactionUpdate")
    String transactionUpdateResponse(@QueryParam("id") String id,
                                     @QueryParam("type") String type,
                                     @QueryParam("owner") String owner,
                                     String information);
}
