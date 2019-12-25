package com.geekbrains.gwt.client;

import com.geekbrains.gwt.common.EmployeeDto;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.*;
import java.util.List;

@Path("/api/v1/employees")
public interface EmployeeClient extends RestService {
    @GET
    void getAll(@HeaderParam("Authorization") String token, MethodCallback<List<EmployeeDto>> employeeDto);

    @DELETE
    @Path("/{id}")
    void delete(@HeaderParam("Authorization") String token, @PathParam("id") String id, MethodCallback<Void> result);

    @GET
    @Path("/{id}")
    void getOne(@HeaderParam("Authorization") String token, MethodCallback<EmployeeDto> result);

    @POST
    void save(@HeaderParam("Authorization") String token, @BeanParam() EmployeeDto employeeDto, MethodCallback<Void> result);
}

