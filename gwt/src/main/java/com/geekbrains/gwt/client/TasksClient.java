package com.geekbrains.gwt.client;

import com.geekbrains.gwt.common.TaskDto;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.*;
import java.util.List;

@Path("/api/v1/tasks")
public interface TasksClient extends RestService {
    @GET
    void getAll(@HeaderParam("Authorization") String token, MethodCallback<List<TaskDto>> tasks);

    @DELETE
    @Path("/{id}")
    void delete(@HeaderParam("Authorization") String token, @PathParam("id") String id, MethodCallback<Void> result);

    @GET
    @Path("/{id}")
    void getOne(@HeaderParam("Authorization") String token, MethodCallback<TaskDto> result);

    @POST
    void save(@HeaderParam("Authorization") String token, @BeanParam() TaskDto taskDto, MethodCallback<Void> result);
}
