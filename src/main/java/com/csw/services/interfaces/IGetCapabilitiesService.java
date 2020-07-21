package com.csw.services.interfaces;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.Path;

public interface IGetCapabilitiesService {
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path(value = "/GetCapabilities")
    String getCapabilitiesResponse();
}
