package com.mayxstudios.resources;

import com.mayxstudios.dtos.CreatePostDTO;
import com.mayxstudios.dtos.PostDTO;
import com.mayxstudios.services.PostService;
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
@Path("/api/posts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@SecurityRequirement(name = "Keycloak")  // Specifies Keycloak as the security scheme
@Tag(name = "Post API", description = "API for managing posts")  // OpenAPI Tag for grouping endpoints
public class PostResource {

    // Service layer for handling post logic (injected)
    private final PostService postService;

    @Inject
    public PostResource(PostService postService) {
        this.postService = postService;
    }

    @GET
    @RolesAllowed("user")
    @Operation(summary = "Get all posts", description = "Fetches a list of all posts")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Success",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostDTO.class)))
    })
    public List<PostDTO> getAllPosts() {
        return postService.getAllPosts();
    }

    @POST
    @RolesAllowed("admin")
    @Operation(summary = "Create a new post", description = "Adds a new post")
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "Post created",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostDTO.class)))
    })
    public Response createPost(CreatePostDTO createPostDTO) {
        PostDTO createdPost = postService.createPost(createPostDTO);
        return Response.status(Response.Status.CREATED).entity(createdPost).build();
    }

    @GET
    @Path("/{postId}")
    @RolesAllowed("user")
    @Operation(summary = "Get a post by ID", description = "Fetches details of a specific post by ID")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Success",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostDTO.class))),
            @APIResponse(responseCode = "404", description = "Post not found")
    })
    public PostDTO getPostById(@PathParam("postId") Long postId) {
        return postService.getPost(postId);
    }

    @DELETE
    @Path("/{postId}")
    @RolesAllowed("admin")  // Only admin can delete posts
    @Operation(summary = "Delete a post", description = "Removes a post by ID")
    @APIResponses(value = {
            @APIResponse(responseCode = "204", description = "Post deleted"),
            @APIResponse(responseCode = "404", description = "Post not found")
    })
    public Response deletePost(@PathParam("postId") Long postId) {
        postService.deletePost(postId);
        return Response.noContent().build();
    }
}