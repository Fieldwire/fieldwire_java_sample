package net.fieldwire;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fieldwire {
    private static final String API_TOKEN = ""; // REPLACE!
    private static final String CONTENT_TYPE = "application/json";

    public static void main(String[] args) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX") // Requires Java 7
                .create();

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint("https://console.fieldwire.net/api/v3")
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

    }

    private static String getAuthorization(String projectToken) {
        String authorization = String.format("Token api=%s", API_TOKEN);

        if (projectToken != null) {
            authorization = String.format("%s, project=%s", authorization, projectToken);
        }

        return authorization;
    }
}
