package com.csw.services.interfaces;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

public interface IGetRecordsByOrganizationService {
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path(value = "/GetRecordsByOrganization")
    String getRecordsByOrganizationResponse(@QueryParam("organization") String organization);
}
