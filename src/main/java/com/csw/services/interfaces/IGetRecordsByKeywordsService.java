package com.csw.services.interfaces;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

public interface IGetRecordsByKeywordsService {
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path(value = "/GetRecordsByKeywords")
    String getRecordsByKeywordsResponse(@QueryParam("keyword") List<String> keywords);
}
