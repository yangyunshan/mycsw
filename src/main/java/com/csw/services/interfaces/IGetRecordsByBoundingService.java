package com.csw.services.interfaces;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

public interface IGetRecordsByBoundingService {
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path(value = "GetRecordsByBounding")
    String getRecordsByBoundingResponse(@QueryParam("left") String left,
                                @QueryParam("down") String down,
                                @QueryParam("right") String right,
                                @QueryParam("up") String up);
}
