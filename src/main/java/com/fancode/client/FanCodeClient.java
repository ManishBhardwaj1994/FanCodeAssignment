package com.fancode.client;

import com.fancode.constant.ApiEndpoint;
import com.fancode.constant.ApiHost;
import com.fancode.constant.RequestType;
import io.restassured.response.Response;

public class FanCodeClient extends RestClient{
    private String HOST = ApiHost.BASE_URL.getApiHost();
    public Response getToDosList(){
        return executeRequest(createRequestSpec(HOST + ApiEndpoint.TO_DO.getEndpoint()), RequestType.GET);
    }

    public Response getUsersList(){
        return executeRequest(createRequestSpec(HOST + ApiEndpoint.USERS.getEndpoint()), RequestType.GET);
    }
}
