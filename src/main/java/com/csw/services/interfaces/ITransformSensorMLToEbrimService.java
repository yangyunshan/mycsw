package com.csw.services.interfaces;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

public interface ITransformSensorMLToEbrimService {
    @POST
    @Path("/TransformSensorMLToEbrim")
    @Consumes(MediaType.TEXT_XML)
    @Produces(MediaType.APPLICATION_XML)
    String transformSensorMLToEbrimResponse(String sensorML);
}
