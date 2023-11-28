package net.fieldwire.endpoints;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface FileUploadEndpoint {
    @POST(".")
    @Multipart
    Call<ResponseBody> postFileToAws(
            @Part("key") String key,
            @Part("policy") String policy,
            @Part("acl") String acl,
            @Part("x-amz-signature") String xAmzSignature,
            @Part("x-amz-date") String xAmzDate,
            @Part("x-amz-credential") String xAmzCredential,
            @Part("x-amz-algorithm") String xAmzAlgorithm,
            @Part("x-amz-meta-original-filename") String xAmzMetaOriginalFilename,
            @Part MultipartBody.Part fileToUpload
    );
}

