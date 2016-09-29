package com.smorales.chat;

import java.util.Random;
import javax.json.Json;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/room")
@Produces(value = MediaType.APPLICATION_JSON)
public class RoomProvider {

    @GET
    public Response getRoom() {
        Integer noRandom = 1;
        return Response.ok(Json.createObjectBuilder()
                .add("roomId", noRandom)
                .build()
        ).build();
    }
}
