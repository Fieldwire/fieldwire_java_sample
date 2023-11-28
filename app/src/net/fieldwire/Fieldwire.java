package net.fieldwire;

import java.io.IOException;

public class Fieldwire {
    // Please refer to the following links for information on getting these tokens:
    //   - refresh_token: https://help.fieldwire.com/hc/en-us/articles/205097173
    //   - access_token: TODO (endpoint present in developers.fieldwire.com)
    //
    // NOTE: `refresh_token` doesn't expire but `access_token` has an expiry on it.
    // `TokenManager` knows how to get a new `access_token` (using `refresh_token`)
    static String REFRESH_TOKEN = ""; // REPLACE
    static String ACCESS_TOKEN = ""; // REPLACE
    static String EMAIL_FOR_PROJECT_INVITE = ""; // REPLACE
    static String LOCAL_FILE_PATH_FOR_PLAN = ""; // REPLACE

    public static void main(String[] args) throws IOException {
        new SampleCalls(
                REFRESH_TOKEN,
                ACCESS_TOKEN
        ).execute(
                // Set the region based on where you want your data to be stored. More info
                // regarding EU servers can be found here:
                // https://help.fieldwire.com/hc/en-us/articles/18799416373531
                SampleCalls.Region.US,
                EMAIL_FOR_PROJECT_INVITE,
                LOCAL_FILE_PATH_FOR_PLAN
        );
    }
}
