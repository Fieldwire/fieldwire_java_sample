package net.fieldwire.endpoints;

import net.fieldwire.models.request.CreateProject;
import net.fieldwire.models.request.CreateSheetUpload;
import net.fieldwire.models.request.CreateTask;
import net.fieldwire.models.request.InviteUser;
import net.fieldwire.models.request.UpdateTask;
import net.fieldwire.models.response.AwsPostToken;
import net.fieldwire.models.response.Project;
import net.fieldwire.models.response.SheetUpload;
import net.fieldwire.models.response.Task;
import net.fieldwire.models.response.ProjectUsersResponse;

import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RegionalEndpoint {
    // Our regional instances (EU & US) store the project data, account
    // templates, checklists etc.
    String API_BASE_URL_EU = "https://client-api.eu.fieldwire.com/api/v3/";
    String API_BASE_URL_US = "https://client-api.us.fieldwire.com/api/v3/";

    @GET("projects")
    Call<List<Project>> getProjects(@Header("Authorization") String authorization);

    @POST("projects")
    Call<Project> createProject(
            @Header("Authorization") String authorization,
            @Body CreateProject data
    );

    @POST("projects/{projectId}/users/invite")
    Call<ProjectUsersResponse> inviteUser(
            @Header("Authorization") String authorization,
            @Path("projectId") UUID projectId,
            @Body InviteUser data
    );

    @POST("projects/{projectId}/tasks")
    Call<Task> createTask(
            @Header("Authorization") String authorization,
            @Path("projectId") UUID projectId,
            @Body CreateTask data
    );

    @POST("projects/{projectId}/sheet_uploads")
    Call<SheetUpload> createSheetUpload(
            @Header("Authorization") String authorization,
            @Path("projectId") UUID projectId,
            @Body CreateSheetUpload data
    );

    @PATCH("projects/{projectId}/tasks/{id}")
    Call<Task> updateTask(
            @Header("Authorization") String authorization,
            @Path("projectId") UUID projectId,
            @Path("id") UUID taskId,
            @Body UpdateTask data
    );

    @POST("aws_post_tokens")
    Call<AwsPostToken> createAwsPostToken(@Header("Authorization") String authorization);
}

