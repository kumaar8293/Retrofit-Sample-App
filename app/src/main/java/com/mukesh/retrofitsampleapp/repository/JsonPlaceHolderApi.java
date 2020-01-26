package com.mukesh.retrofitsampleapp.repository;


import com.mukesh.retrofitsampleapp.datamodel.PostModel;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface JsonPlaceHolderApi {

    //https://jsonplaceholder.typicode.com/posts
    //Simple Get request
    @GET("posts")
    Call<List<PostModel>> simpleGetRequest();

    //Get request with Query
    //https://jsonplaceholder.typicode.com/posts?userId=10
    @GET("posts")
    Call<List<PostModel>> getRequestWithQuery(@Query("userId") int userId);

    //https://jsonplaceholder.typicode.com/posts?userId=1&_sort=id&_order=desc
    // get Query with multiple params
    @GET("posts")
    Call<List<PostModel>> getRequestWithMultipleQuery(
            @Query("userId") int userId,
            @Query("_sort") String sort,
            @Query("_order") String order
    );

    //https://jsonplaceholder.typicode.com/posts?userId=1&userId=4&_sort=id&_order=desc
    @GET("posts")
    Call<List<PostModel>> getRequestWithMultipleUserIdQuery(
            @Query("userId") int userId,
            @Query("userId") int userId2,
            @Query("_sort") String sort,
            @Query("_order") String order);


    /**
     * we can pass @Params userId as in Integer instead of int ,
     * if we want to pass null as userId
     * We can also pass Array or ArrayList as userId ,if want to pass multiple userId
     * https://jsonplaceholder.typicode.com/posts?userId=1&userId=5&userId=17&_sort=id&_order=desc
     */
    @GET("posts")
    Call<List<PostModel>> getRequestWithMultipleUserIdInArrayQuery(
            @Query("userId") Integer[] userId,
            @Query("_sort") String sort,
            @Query("_order") String desc);

    //https://jsonplaceholder.typicode.com/posts?_order=desc&userId=1&_sort=id
    @GET("posts")
    Call<List<PostModel>> getRequestWithMapQueryQuery(@QueryMap Map<String, Object> params);

    //https://jsonplaceholder.typicode.com/posts?userId=1
    @GET
    Call<List<PostModel>> getRequestWithURL(@Url String url);

    //NOT WORKING
    @GET("posts?userId={id}")
    Call<List<PostModel>> getRequestWithPathType(@Path("id") int data);

    //------------------------------- POST REQUEST ----------------------------

    /**
     * This is a simple POST request to create own post
     * postData will be automatically converted to JSON format data
     */
    @POST("posts")
    Call<PostModel> simplePostRequest(@Body PostModel postData);

    //FORM URLENCODED
    @FormUrlEncoded
    @POST("posts")
    Call<PostModel> postRequestWithFormUrlEncoded(
            @Field("userId") int userId,
            @Field("title") String title,
            @Field("body") String text
    );

    //Map Query
    @FormUrlEncoded
    @POST("posts")
    Call<PostModel> postRequestWithMapData(@FieldMap Map<String, Object> params);


    //----------------------- PUT PATCH and DELETE ---------------------------

    /**
     * PUT and PATCH are the HTTP methods that we use to UPDATE data on a server.
     * While PATCH only changes the specific fields we send over, PUT completely replaces the existing entity
     * with the one in the request body (or alternatively creates it).
     * A DELETE request deletes an existing resource.
     * PUT, PATCH and DELETE are usually applied on a single item rather than a collection as a whole.
     * As usual, we declare these methods in our API Java interface and annotate them with their corresponding HTTP call:
     *
     * @PUT, @PATCH or @DELETE. With @Path placeholders we dynamically specify the URL endpoint on which we want to modify a resource.
     * PUT and PATCH both send a @Body annotated request body, which the GSON converter serializes into the JSON format.
     */


}
