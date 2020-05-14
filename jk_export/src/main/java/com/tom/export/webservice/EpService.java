package com.tom.export.webservice;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.tom.export.vo.ExportResult;
import com.tom.export.vo.ExportVo;

@Produces("*/*")
public interface EpService {
    @POST
    @Path("/user")
    @Consumes({"application/xml", "application/json"})
    public void exportE(ExportVo export) throws Exception;

    @GET
    @Path("/user/{id}")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public ExportResult getResult(@PathParam("id") String id) throws Exception;
}
