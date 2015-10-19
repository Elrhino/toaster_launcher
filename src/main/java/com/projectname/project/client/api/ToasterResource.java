package com.projectname.project.client.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.gwtplatform.dispatch.rest.shared.RestAction;

import static com.projectname.project.client.api.ApiPaths.TOASTER;

@Path(TOASTER)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface ToasterResource {
    @GET
    RestAction<Toaster> getToaster();
}
