package net.fieldwire;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit.RestAdapter;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import retrofit.mime.TypedFile;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.io.File;

public class Fieldwire {
    private static final String API_TOKEN = ""; // REPLACE!
    private static final String CONTENT_TYPE = "application/json";

    private static final String END_POINT = "https://console.fieldwire.net";

    public static Response doAWSPostRequest(AWSPostToken awsPostToken, String filename)
    {
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(awsPostToken.post_address)
                .build();

        AWSS3PostService service = adapter.create(AWSS3PostService.class);

        File file = new File(filename);
        System.out.println(file.getName());

        return service.postFileToAWS(
                awsPostToken.post_parameters.get("key"),
                awsPostToken.post_parameters.get("policy"),
                awsPostToken.post_parameters.get("x-amz-signature"),
                awsPostToken.post_parameters.get("x-amz-date"),
                awsPostToken.post_parameters.get("x-amz-credential"),
                awsPostToken.post_parameters.get("acl"),
                awsPostToken.post_parameters.get("x-amz-algorithm"),
                new TypedFile("multipart/form-data", file));
    }

    public static void main(String[] args) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX") // Requires Java 7
                .create();

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(END_POINT + "/api/v3")
                .setConverter(new GsonConverter(gson))
                .build();

        FieldwireService service  = adapter.create(FieldwireService.class);

        List<Project> projects = service.getProjects(getAuthorization(null));
        System.out.println(projects);

        Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("name", "New Project");

        Project project = service.createProject(getAuthorization(null), CONTENT_TYPE, attributes);
        System.out.println(project);

        attributes = new HashMap<String, Object>();
        attributes.put("email", ""); // REPLACE!
        UserResponse userResponse = service.inviteUser(getAuthorization(project.access_token), CONTENT_TYPE, project.id, attributes);
        System.out.println(userResponse.users);

        attributes = new HashMap<String, Object>();
        attributes.put("name", "Todo item");
        attributes.put("priority", 1);
        attributes.put("owner_user_id", userResponse.users.get(0).id);
        attributes.put("is_local", false);

        Task task = service.createTask(getAuthorization(project.access_token), CONTENT_TYPE, project.id, attributes);
        System.out.println(task);

        attributes = new HashMap<String, Object>();
        attributes.put("priority", 2);
        attributes.put("due_date", new Date());

        task = service.updateTask(getAuthorization(project.access_token), CONTENT_TYPE, project.id, task.id, attributes);
        System.out.println(task);

        System.out.println("project.access_token" + project.access_token);

        AWSPostToken awsPostToken = service.getAWSPostToken(
                getAuthorization(null)
                , CONTENT_TYPE);
        System.out.println(awsPostToken);
      
        String filename = ""; // REPLACE
        Response response = doAWSPostRequest(awsPostToken, filename);

        if(response.getStatus() == 200 || response.getStatus() == 204)
        {
            File file = new File(filename);
            String fileUrl = getFileUrl(awsPostToken, file);

            attributes = new HashMap<String, Object>();
            attributes.put("name", file.getName());
            attributes.put("file_url", fileUrl);
            attributes.put("file_size", file.length());
            attributes.put("user_id", userResponse.users.get(0).id);

            SheetUpload sheetUpload = service.createSheetUpload(getAuthorization(project.access_token), CONTENT_TYPE, project.id, attributes);
            System.out.println(sheetUpload);
        }
    }

    /*
     *  This function is not necessary to post the file on AWS. It is called after, to get the url of the file.
     *  AWS is doing the same substitution.
     */
    private static String getFileUrl(AWSPostToken awsPostToken, File file)
    {
        String aws_key = awsPostToken.post_parameters.get("key").replaceAll("\\$\\{filename\\}", file.getName());

        return awsPostToken.post_address + '/' + aws_key;
    }

    private static String getAuthorization(String projectToken) {
        String authorization = String.format("Token api=%s", API_TOKEN);

        if (projectToken != null) {
            authorization = String.format("%s, project=%s", authorization, projectToken);
        }

        return authorization;
    }
}
