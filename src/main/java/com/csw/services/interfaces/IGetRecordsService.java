package com.csw.services.interfaces;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

public interface IGetRecordsService {
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path(value = "/GetRecords")
    String getRecordsResponse();
}
