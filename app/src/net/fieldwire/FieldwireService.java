package net.fieldwire;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.PATCH;
import retrofit.http.POST;
import retrofit.http.Path;

public interface FieldwireService {

    @GET("/projects")
    List<Project> getProjects(@Header("Authorization") String authorization);

    @POST("/projects")
    Project createProject(@Header("Authorization") String authorization, @Header("Content-type") String contentType, @Body Map<String, Object> project);

	@POST("/projects/{projectId}/users/invite")
    UserResponse inviteUser(@Header("Authorization") String authorization, @Header("Content-type") String contentType, @Path("projectId") UUID id, @Body Map<String, Object> emails);

    @POST("/projects/{projectId}/tasks")
    Task createTask(@Header("Authorization") String authorization, @Header("Content-type") String contentType, @Path("projectId") UUID projectId, @Body Map<String, Object> task);

    @PATCH("/projects/{projectId}/tasks/{id}")
    Task updateTask(@Header("Authorization") String authorization, @Header("Content-type") String contentType, @Path("projectId") UUID projectId, @Path("id") UUID id, @Body Map<String, Object> task);
}
