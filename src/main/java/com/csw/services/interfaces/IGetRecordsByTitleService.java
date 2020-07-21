package com.csw.services.interfaces;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

public interface IGetRecordsByTitleService {
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/GetRecordsByTitle")
    String getRecordsByTitleResponse(@QueryParam("title") String title);
}
