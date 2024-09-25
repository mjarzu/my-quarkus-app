package com.mayxstudios.resources;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.annotations.cache.NoCache;

@Path("/api/users")
public class UsersResource {

    @Inject
    SecurityIdentity securityIdentity;

    @GET
    @Path("/me")
    @RolesAllowed("admin")
    @NoCache
    @Produces(MediaType.TEXT_PLAIN)
    public String user() {
        return String.format("Granted standard user: %s", securityIdentity.getPrincipal().getName()) ;
    }
}
