package net.fieldwire;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.fieldwire.endpoints.FileUploadEndpoint;
import net.fieldwire.endpoints.RegionalEndpoint;
import net.fieldwire.endpoints.SuperEndpoint;
import net.fieldwire.models.request.CreateProject;
import net.fieldwire.models.request.CreateSheetUpload;
import net.fieldwire.models.request.CreateTask;
import net.fieldwire.models.request.InviteUser;
import net.fieldwire.models.request.UpdateTask;
import net.fieldwire.models.response.AccountUserResponse;
import net.fieldwire.models.response.AwsPostToken;
import net.fieldwire.models.response.Project;
import net.fieldwire.models.response.ProjectUser;
import net.fieldwire.models.response.ProjectUsersResponse;
import net.fieldwire.models.response.SheetUpload;
import net.fieldwire.models.response.Task;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SampleCalls {
    @NotNull
    private final TokenManager tokenManager;
    @NotNull
    private final OkHttpClient okHttpClient;
    @NotNull
    private final Gson gson;

    public enum Region {
        EU,
        US,
    }

    public SampleCalls(
            @NotNull String refreshToken,
            @NotNull String accessToken
    ) {
        // Build gson to suit the needs of FW data deserialization
        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX")
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        // Build the components that takes care of managing our tokens
        tokenManager = new TokenManager(refreshToken, accessToken, gson);

        // Create an HTTP client with our authenticator middleware which takes
        // care of refreshing the access token when it expires (or is invalid)
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new HeadersAddingInterceptor())
                .addInterceptor(new HttpLoggingInterceptor().setLevel(Level.BASIC))
                .build();
    }

    public void execute(
            @NotNull Region region,
            @NotNull String emailForProjectInvite,
            @NotNull String localFilePathForPlan
    ) throws IOException {
        executeSampleSuperCalls();
        executeSampleRegionalCalls(region, emailForProjectInvite, localFilePathForPlan);
    }

    // Super endpoints are used for account & users info
    private void executeSampleSuperCalls() throws IOException {
        // Build the impl that allows super endpoints to be called
        SuperEndpoint endpoint = new Retrofit.Builder()
                .baseUrl(SuperEndpoint.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(this.okHttpClient)
                .build()
                .create(SuperEndpoint.class);

        List<AccountUserResponse> users = tokenManager.executeWithToken(endpoint::getAccountUsers);
        String users_info = users
                .stream()
                .map(u -> String.format("%s: %s", u.user().accountRole, u.user().email))
                .sorted()
                .collect(Collectors.joining("\n"));

        log_info("Users in account", users_info);
    }

    // Regional endpoints are used for projects, templates etc.
    private void executeSampleRegionalCalls(
            @NotNull Region region,
            @NotNull String emailForProjectInvite,
            @NotNull String localFilePathForPlan
    ) throws IOException {
        // Based on the region, figure out which base URL we want to talk with
        final String regionalBaseUrl;
        if (region == Region.EU) {
            regionalBaseUrl = RegionalEndpoint.API_BASE_URL_EU;
        } else if (region == Region.US) {
            regionalBaseUrl = RegionalEndpoint.API_BASE_URL_US;
        } else {
            throw new RuntimeException("Unrecognizable region: " + region);
        }

        // Build the impl that allows super endpoints to be called
        RegionalEndpoint endpoint = new Retrofit.Builder()
                .baseUrl(regionalBaseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(this.okHttpClient)
                .build()
                .create(RegionalEndpoint.class);

        //-----------------------------------------------
        // List projects
        //-----------------------------------------------

        List<Project> projects = tokenManager.executeWithToken(endpoint::getProjects);
        String projects_info = projects
                .stream()
                .map(p -> String.format("%s: %s", p.id, p.name))
                .sorted()
                .collect(Collectors.joining("\n"));

        log_info("Projects in account", projects_info);

        //-----------------------------------------------
        // Create a new project
        //-----------------------------------------------

        Project project = tokenManager.executeWithToken(authz -> endpoint.createProject(
                authz,
                new CreateProject("New project")
        ));

        log_info("Created project", project.toString());

        //-----------------------------------------------
        // Invite a user to a project
        //-----------------------------------------------

        ProjectUsersResponse projectUsersResponse = tokenManager.executeWithToken(
                authz -> endpoint.inviteUser(
                    authz,
                    project.id,
                    new InviteUser(emailForProjectInvite)
                )
        );

        ProjectUser user = projectUsersResponse.users.get(0);
        log_info("Invited user to project", user.toString());

        //-----------------------------------------------
        // Add a task to a project
        //-----------------------------------------------

        Task createdTask = tokenManager.executeWithToken(
                authz -> endpoint.createTask(
                        authz,
                        project.id,
                        new CreateTask("Todo", 1, user.id, user.id, user.id)
                )
        );

        log_info("Created task", createdTask.toString());

        //-----------------------------------------------
        // Update a task
        //-----------------------------------------------

        Task updatedTask = tokenManager.executeWithToken(
                authz -> endpoint.updateTask(
                        authz,
                        project.id,
                        createdTask.id,
                        new UpdateTask(2, new Date(), user.id)
                )
        );

        log_info("Updated task", updatedTask.toString());

        //-----------------------------------------------
        // Upload a plan to a project. Multiple steps:
        //  - acquire token to upload files to S3
        //  - upload plan using the token acquired above
        //  - inform Fieldwire about the upload plan
        //-----------------------------------------------

        // Acquire token
        AwsPostToken awsPostToken = tokenManager.executeWithToken(endpoint::createAwsPostToken);

        // Use a specific client for AWS interaction so we don't send FW
        // specific info (ex: this doesn't add the `HeaderAddingInterceptor`)
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(Level.BASIC))
                .build();

        // Upload file
        File file = new File(localFilePathForPlan);
        Response<ResponseBody> response = new Retrofit.Builder()
                .baseUrl(awsPostToken.postAddress() + "/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(client)
                .build()
                .create(FileUploadEndpoint.class)
                .postFileToAws(
                        awsPostToken.postParameters().get("key"),
                        awsPostToken.postParameters().get("policy"),
                        awsPostToken.postParameters().get("acl"),
                        awsPostToken.postParameters().get("x-amz-signature"),
                        awsPostToken.postParameters().get("x-amz-date"),
                        awsPostToken.postParameters().get("x-amz-credential"),
                        awsPostToken.postParameters().get("x-amz-algorithm"),
                        awsPostToken.postParameters().get("x-amz-meta-original-filename"),
                        MultipartBody.Part.createFormData(
                                "file",
                                file.getName(),
                                RequestBody.create(
                                    file,
                                    MediaType.parse("multipart/form-data")
                                )
                        )
                )
                .execute();

        if (!response.isSuccessful()) {
            throw new IOException(String.format(
                    "Response code: %s\nResponse body: %s",
                    response.code(),
                    response.errorBody().string()
            ));
        }

        // Build URL & inform Fieldwire
        String aws_key = awsPostToken
                .postParameters()
                .get("key")
                .replaceAll("\\$\\{filename\\}", file.getName());

        String file_url = awsPostToken.postAddress() + '/' + aws_key;
        SheetUpload sheetUpload = tokenManager.executeWithToken(
                authz -> endpoint.createSheetUpload(
                        authz,
                        project.id,
                        new CreateSheetUpload(
                                file.getName(),
                                file_url,
                                file.length(),
                                user.id
                        )
                )
        );

        log_info("Plan uploaded for processing", sheetUpload.toString());
    }

    private void log_info(@NotNull String purpose, @NotNull String data) {
        System.out.println("===============================================\n");
        System.out.println(purpose + "\n");
        System.out.println(data);
        System.out.println("===============================================\n\n");
    }
}
