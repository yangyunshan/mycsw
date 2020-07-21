package com.csw.services.interfaces;

import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

public interface ITransactionDeleteService {
    @DELETE
    @Path(value = "/TransactionDelete")
    @Produces(MediaType.APPLICATION_XML)
    String transactionDeleteResponse(@QueryParam("id") String id);
}
