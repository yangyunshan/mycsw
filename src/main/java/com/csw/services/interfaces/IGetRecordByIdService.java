package com.csw.services.interfaces;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

public interface IGetRecordByIdService {
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path(value = "/GetRecordById")
    String getRecordByIdResponse(@QueryParam("id") String id);
}
