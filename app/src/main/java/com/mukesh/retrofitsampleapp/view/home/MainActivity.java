package com.mukesh.retrofitsampleapp.view.home;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mukesh.retrofitsampleapp.R;
import com.mukesh.retrofitsampleapp.datamodel.PostModel;
import com.mukesh.retrofitsampleapp.repository.ApiConfig;
import com.mukesh.retrofitsampleapp.repository.JsonPlaceHolderApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private List<String> nameList;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prepareData();
       //Getting JsonPlaceHolderApi reference
        jsonPlaceHolderApi = ApiConfig.getRetrofit().create(JsonPlaceHolderApi.class);
        textViewResult = findViewById(R.id.tv_data);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        HomeAdapter adapter = new HomeAdapter(nameList) {
            @Override
            public void customOnClick(int position) {
                makeApiRequest(position);
            }
        };
        recyclerView.setAdapter(adapter);
    }

    //Types or request
    //Number of buttons loading
    private void prepareData() {
        nameList = new ArrayList<>();
        nameList.add("Simple Get request");
        nameList.add("Get Request With Query");
        nameList.add("Get Request with Multiple Query");
        nameList.add("Get Request with Multiple UserId Query");
        nameList.add("Get with Multiple UserId In Array Query");
        nameList.add("Get Request With Map Query");
        nameList.add("Get Request With URL");
        nameList.add("Get Request With Path type");
        nameList.add("Simple Post Request");
        nameList.add("Post Request with FormUrlEncoded");
        nameList.add("Post Request with Map Data");
        nameList.add("Simple Put Request");
        nameList.add("Simple Patch Request");
        nameList.add("Simple Delete Request");
    }

    //Validation after button clicked
    //Validation for which methods needs to be called after button clicked
    private void makeApiRequest(int position) {

        Log.d(TAG, "makeApiRequest: " + position);

        switch (position) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                getRequest(position);
                break;
            case 8:
            case 9:
            case 10:
                postRequest(position);
                break;
            case 11:
                putRequest();
                break;
            case 12:
                patchRequest();
                break;

            case 13:
                deleteRequest();
                break;
        }
    }

    //All kind of GET request
    private void getRequest(int i) {
        Call<List<PostModel>> call = null;
        switch (i) {
            case 0:
                // https://jsonplaceholder.typicode.com/posts
                call = jsonPlaceHolderApi.simpleGetRequest();
                break;
            case 1:
                //https://jsonplaceholder.typicode.com/posts?userId=10
                call = jsonPlaceHolderApi.getRequestWithQuery(10);
                break;
            case 2:
                //https://jsonplaceholder.typicode.com/posts?userId=1&_sort=id&_order=desc
                call = jsonPlaceHolderApi.getRequestWithMultipleQuery(4, "id", "desc");
                break;
            case 3:
                //https://jsonplaceholder.typicode.com/posts?userId=1&userId=4&_sort=id&_order=desc
                call = jsonPlaceHolderApi.getRequestWithMultipleUserIdQuery(1, 4, "id", "desc");

                break;
            case 4:
                //https://jsonplaceholder.typicode.com/posts?userId=1&userId=5&userId=17&_sort=id&_order=desc
                call = jsonPlaceHolderApi.getRequestWithMultipleUserIdInArrayQuery(new Integer[]{1, 5, 17}, "id", "desc");
                break;
            case 5:
                //https://jsonplaceholder.typicode.com/posts?_order=desc&userId=1&_sort=id
                Map<String, Object> params = new HashMap<>();
                params.put("userId", "1");
                params.put("_sort", "id");
                params.put("_order", "desc");

                call = jsonPlaceHolderApi.getRequestWithMapQueryQuery(params);

                break;
            case 6:
                //https://jsonplaceholder.typicode.com/posts?userId=1
                call = jsonPlaceHolderApi.getRequestWithURL("posts?userId=1");
                break;

            case 7:
                //https://jsonplaceholder.typicode.com/posts?userId=1
                // call = jsonPlaceHolderApi.getRequestWithPathType(10);
                Toast.makeText(this, "No working", Toast.LENGTH_SHORT).show();

                break;

        }
        if (call == null)
            return;


        //call.execute(); can't run in main thread and we need to run asynchronously
        call.enqueue(new Callback<List<PostModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<PostModel>> call,
                                   @NonNull Response<List<PostModel>> response) {
                textViewResult.setText("");
                if (!response.isSuccessful()) {
                    textViewResult.setText(("Code " + response.code()));
                    return;
                }
                List<PostModel> data = response.body();
                if (data != null)
                    for (PostModel model : data) {
                        String content = "";
                        content += "ID: " + model.getId() + "\n" + "User Id: " + model.getUserId()
                                + "\nTitle: " + model.getTitle() + "\nText: " + model.getTextData() + "\n\n";
                        textViewResult.append(content);
                    }
            }

            @Override
            public void onFailure(@NonNull Call<List<PostModel>> call, @NonNull Throwable t) {
                textViewResult.setText(t.getMessage());

            }
        });
    }

    //Various POST request
    private void postRequest(int position) {

        Call<PostModel> call = null;

        switch (position) {
            case 8:
                PostModel postData = new PostModel(25, "This is my second post ", "Post request successful");
                //Normal Post request with PostModel ,JSON Object
                call = jsonPlaceHolderApi.simplePostRequest(postData);

                break;
            case 9:
                call = jsonPlaceHolderApi.postRequestWithFormUrlEncoded(101, "This is my new Post", "Posted successfully");
                break;
            case 10:
                Map<String, Object> params = new HashMap<>();
                params.put("userId", 25);
                params.put("title", "This is Map post request");
                params.put("body", "Request successful");
                call = jsonPlaceHolderApi.postRequestWithMapData(params);
                break;
        }
        if (call == null)
            return;
        call.enqueue(new Callback<PostModel>() {

            @Override
            public void onResponse(@NonNull Call<PostModel> call,
                                   @NonNull Response<PostModel> response) {

                if (!response.isSuccessful()) {
                    textViewResult.setText(("Code : " + response.code()));

                    return;
                }
                PostModel postData = response.body();
                if (postData == null)
                    return;

                String content = "";
                content += "Code : " + response.code() + "\n";
                content += "ID : " + postData.getId() + "\n";
                content += "User id : " + postData.getUserId() + "\n";
                content += "Title : " + postData.getTitle() + "\n";
                content += "Body : " + postData.getTextData() + "\n";

                textViewResult.setText(content);
            }

            @Override
            public void onFailure(@NonNull Call<PostModel> call, @NonNull Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });

    }

    //Simple PUT request
    private void putRequest() {

        PostModel data = new PostModel(5, "New Title ", "Put request successful");

        Call<PostModel> call = jsonPlaceHolderApi.simplePutRequest(1, data);

        call.enqueue(new Callback<PostModel>() {
            @Override
            public void onResponse(@NonNull Call<PostModel> call, @NonNull Response<PostModel> response) {

                if (!response.isSuccessful()) {
                    textViewResult.setText(("Code : " + response.code()));
                    return;
                }
                PostModel postData = response.body();

                if (postData != null) {

                    String content = "";
                    content += "Code : " + response.code() + "\n";
                    content += "ID : " + postData.getId() + "\n";
                    content += "User id : " + postData.getUserId() + "\n";
                    content += "Title : " + postData.getTitle() + "\n";
                    content += "Body : " + postData.getTextData() + "\n";

                    textViewResult.setText(content);
                }

            }

            @Override
            public void onFailure(@NonNull Call<PostModel> call, @NonNull Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    //Simple Patch Request
    private void patchRequest() {
        PostModel data = new PostModel(5, "New Title With Patch request ", null);

        Call<PostModel> call = jsonPlaceHolderApi.simplePatchRequest(1, data);
        call.enqueue(new Callback<PostModel>() {
            @Override
            public void onResponse(@NonNull Call<PostModel> call, @NonNull Response<PostModel> response) {

                if (!response.isSuccessful()) {
                    textViewResult.setText(("Code : " + response.code()));
                    return;
                }
                PostModel postData = response.body();
                if (postData == null)
                    return;
                String content = "";
                content += "Code : " + response.code() + "\n";
                content += "ID : " + postData.getId() + "\n";
                content += "User id : " + postData.getUserId() + "\n";
                content += "Title : " + postData.getTitle() + "\n";
                content += "Body : " + postData.getTextData() + "\n";

                textViewResult.setText(content);

            }

            @Override
            public void onFailure(@NonNull Call<PostModel> call, @NonNull Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    //Simple delete request
    private void deleteRequest() {
        Call<Void> deleteRequest = jsonPlaceHolderApi.simpleDeleteRequest(5);

        deleteRequest.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {

                textViewResult.setText(("Code: " + response.code() + " is success " + response.isSuccessful()));
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {

                textViewResult.setText(t.getMessage());
            }
        });
    }
}
