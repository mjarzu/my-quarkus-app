package com.mayxstudios.resources;

import com.mayxstudios.dtos.CommentDTO;
import com.mayxstudios.dtos.CreateCommentDTO;
import com.mayxstudios.services.CommentService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@ApplicationScoped
@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@SecurityRequirement(name = "Keycloak")  // Specifies Keycloak as the security scheme
@Tag(name = "Comment API", description = "API for managing comments on posts")  // OpenAPI Tag
public class CommentResource {

    private final CommentService commentService;

    @Inject
    public CommentResource(CommentService commentService) {
        this.commentService = commentService;
    }

    @GET
    @Path("/posts/{postId}/comments")
    @RolesAllowed("user")
    @Operation(summary = "Get all comments", description = "Fetches a list of all comments for a post")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Success",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentDTO.class)))
    })
    public List<CommentDTO> getAllComments(@PathParam("postId") Long postId) {
        return commentService.getAllCommentsForPost(postId);
    }

    @POST
    @Path("/posts/{postId}/comments")
    @RolesAllowed("user")  // Only users with the "user" role can create comments
    @Operation(summary = "Create a new comment", description = "Adds a new comment for a post")
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "Comment created",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentDTO.class)))
    })
    public Response createComment(@PathParam("postId") Long postId, CreateCommentDTO createCommentDTO) {
        return Response.status(Response.Status.CREATED).entity(commentService.createComment(createCommentDTO, postId)).build();
    }

    @DELETE
    @Path("/comments/{commentId}")
    @RolesAllowed("admin")  // Only admin can delete comments
    @Operation(summary = "Delete a comment", description = "Removes a comment by ID")
    @APIResponses(value = {
            @APIResponse(responseCode = "204", description = "Comment deleted"),
            @APIResponse(responseCode = "404", description = "Comment not found")
    })
    public Response deleteComment(@PathParam("commentId") Long commentId) {
        commentService.deleteComment(commentId);
        return Response.noContent().build();
    }
}