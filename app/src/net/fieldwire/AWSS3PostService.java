package net.fieldwire;

import com.squareup.okhttp.ResponseBody;
import retrofit.client.Response;
import retrofit.http.*;
import retrofit.mime.TypedFile;

import retrofit.Callback;

import java.io.File;
import java.util.Map;
import java.util.UUID;

public interface AWSS3PostService {
    @POST("/")
    @Multipart
    Response postFileToAWS(
            @Part("key") String key,
            @Part("policy") String policy,
            @Part("acl") String acl,
            @Part("x-amz-signature") String x_amz_signature,
            @Part("x-amz-date") String x_amz_date,
            @Part("x-amz-credential") String x_amz_credential,
            @Part("x-amz-algorithm") String x_amz_algorithm,
            @Part("x-amz-meta-original-filename") String x_amz_meta_original_filename,
            @Part("file") TypedFile fileToUpload);
}

