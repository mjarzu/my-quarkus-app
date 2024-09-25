package com.mayxstudios.exceptionhandler;

import com.mayxstudios.exceptions.InvalidDataException;
import com.mayxstudios.exceptions.ResourceNotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.core.MediaType;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        if (exception instanceof ResourceNotFoundException) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(exception.getMessage()))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } else if (exception instanceof InvalidDataException) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(exception.getMessage()))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }

        // Generic fallback for other exceptions
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse("An unexpected error occurred"))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    // Inner class to format the error response
    public static class ErrorResponse {
        public String error;

        public ErrorResponse(String error) {
            this.error = error;
        }
    }
}