package com.fancode.client;

import com.fancode.constant.RequestType;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
public class RestClient {
    public Response executeRequest(RequestSpecification request, RequestType requestType){
        switch (requestType){
            case GET:
                return RestAssured.given().spec(request).when().get();

            case POST:
                return RestAssured.given().spec(request).when().post();

            case PUT:
                return RestAssured.given().spec(request).when().put();

            case DELETE:
                return RestAssured.given().spec(request).when().delete();

            default:
                return null;
        }
    }

    public RequestSpecification createRequestSpec(String url){
        return RestAssured.given().baseUri(url);
    }
}
