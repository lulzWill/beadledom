package com.cerner.beadledom.jaxrs.exceptionmapping;

import com.cerner.beadledom.json.common.model.JsonError;
import com.fasterxml.jackson.core.JsonParseException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * An {@link ExceptionMapper} for the {@link JsonParseException} family of exceptions.
 *
 * <p>The intention of this exception mapper is to handle Jackson parse exceptions, and turn them
 * into a 400 response in the standard JSON error format.
 *
 * @author John Leacox
 * @since 2.6.1
 */
@Provider
public class JsonParseExceptionMapper implements ExceptionMapper<JsonParseException> {
  @Override
  public Response toResponse(JsonParseException e) {
    return Response
        .status(Response.Status.BAD_REQUEST)
        .entity(JsonError.builder()
            .code(Response.Status.BAD_REQUEST.getStatusCode())
            .message("Unable to parse request JSON")
            .build())
        .type(MediaType.APPLICATION_JSON)
        .build();
  }
}
