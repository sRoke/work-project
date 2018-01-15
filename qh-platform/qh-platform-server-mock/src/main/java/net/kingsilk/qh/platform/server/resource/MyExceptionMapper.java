package net.kingsilk.qh.platform.server.resource;

import org.springframework.stereotype.*;

import javax.ws.rs.core.*;
import javax.ws.rs.ext.*;
import java.util.*;

/**
 *
 */
@Component
public class MyExceptionMapper implements ExceptionMapper<IllegalArgumentException> {
    @Override
    public Response toResponse(IllegalArgumentException exception) {

        System.out.println("==========" + exception.getMessage());
        return Response
                .status(Response.Status.BAD_REQUEST)
                .type(MediaType.APPLICATION_JSON)
                .entity(Arrays.asList(exception.getMessage()))
                .build();
    }
}
