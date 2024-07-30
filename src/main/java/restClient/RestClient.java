package restClient;

import constant.APIhttpStatus;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class RestClient {

    private String baseUri;
    private boolean isAuthorizationHeaderAdded = false;
    private RequestSpecBuilder specBuilder;

    Properties prop;

    public RestClient(Properties prop, String baseURI) {
        specBuilder = new RequestSpecBuilder();
        this.prop = prop;
        this.baseUri = baseURI;
    }

    private void setRequestContentType(String contentType) {
        switch (contentType) {
            case "json":
                specBuilder.setContentType(ContentType.JSON);
                break;
            case "xml":
                specBuilder.setContentType(ContentType.XML);
                break;
            case "text":
                specBuilder.setContentType(ContentType.TEXT);
                break;
            default:
                break;
        }
    }

    public void restAuthorizationHeaderFlag() {
        isAuthorizationHeaderAdded = false;
    }

    public void addAuthorizationHeader() {
        if (!isAuthorizationHeaderAdded) {
            specBuilder.addHeader("Authorization", "Bearer " + prop.getProperty("token"));
            isAuthorizationHeaderAdded = true;
        }
    }

    private RequestSpecification createRequestSpec(boolean includeAuth) {
        specBuilder.setBaseUri(baseUri);
        if (includeAuth)
            addAuthorizationHeader();
        return specBuilder.build();
    }

    private RequestSpecification createRequestSpec(Map<String, String> headerMap, boolean includeAuth) {
        specBuilder.setBaseUri(baseUri);
        if (includeAuth)
            addAuthorizationHeader();
        if (headerMap != null)
            specBuilder.addHeaders(headerMap);
        return specBuilder.build();
    }

    private RequestSpecification createRequestSpec(Map<String, String> headerMap, Map<String, Object> querParams, boolean includeAuth) {
        specBuilder.setBaseUri(baseUri);
        if (includeAuth)
            addAuthorizationHeader();
        if (headerMap != null)
            specBuilder.addHeaders(headerMap);
        if (querParams != null)
            specBuilder.addQueryParams(querParams);
        return specBuilder.build();
    }

    private RequestSpecification createRequestSpec(Object requestBody, String contentType, boolean includeAuth) {
        specBuilder
                .setBaseUri(baseUri)
                .setContentType(ContentType.JSON);

        if (includeAuth) {
            addAuthorizationHeader();
        }

        setRequestContentType(contentType);

        if (requestBody != null) {
            specBuilder.setBody(requestBody);
        }

        return specBuilder.build();
    }


    private RequestSpecification createRequestSpec(Object requestBody, String contentType, Map<String, String> headersMap, boolean includeAuth) {
        specBuilder
                .setBaseUri(baseUri)
                .setContentType(ContentType.JSON)
                .addHeaders(headersMap);

        if (includeAuth) {
            addAuthorizationHeader();
        }

        setRequestContentType(contentType);

        if (requestBody != null) {
            specBuilder.setBody(requestBody);
        }

        return specBuilder.build();
    }

    public Response get(String serviceUrl, boolean includeAuth, boolean log) {

        if (log) {
            return given(createRequestSpec(includeAuth)).log().all()
                    .when()
                    .get(serviceUrl);
        }
        return given(createRequestSpec(includeAuth)).when().get(serviceUrl);
    }


    public Response get(String serviceUrl, Map<String, String> headersMap, boolean includeAuth, boolean log) {

        if (log) {
            return given(createRequestSpec(headersMap, includeAuth)).log().all()
                    .when()
                    .get(serviceUrl);
        }
        return given(createRequestSpec(headersMap, includeAuth)).when().get(serviceUrl);
    }


    public Response get(String serviceUrl, Map<String, Object> queryParams, Map<String, String> headersMap, boolean includeAuth, boolean log) {

        if (log) {
            return given(createRequestSpec(headersMap, queryParams, includeAuth)).log().all()
                    .when()
                    .get(serviceUrl);
        }
        return given(createRequestSpec(headersMap, queryParams, includeAuth)).when().get(serviceUrl);
    }


    public Response post(String serviceUrl, String contentType, Object requestBody, boolean includeAuth, boolean log) {

        if (log) {
            return given(createRequestSpec(requestBody, contentType, includeAuth)).log().all()
                    .when()
                    .post(serviceUrl);
        }
        return given(createRequestSpec(requestBody, contentType, includeAuth)).when().post(serviceUrl);

    }


    public Response post(String serviceUrl, String contentType, Object requestBody, Map<String, String> headersMap, boolean includeAuth, boolean log) {

        if (log) {
            return given(createRequestSpec(requestBody, contentType, headersMap, includeAuth)).log().all()
                    .when()
                    .post(serviceUrl);
        }
        return given(createRequestSpec(requestBody, contentType, headersMap, includeAuth)).when().post(serviceUrl);

    }

    public Response put(String serviceUrl, String contentType, Object requestBody, boolean includeAuth, boolean log) {

        if (log) {
            return given(createRequestSpec(requestBody, contentType, includeAuth)).log().all()
                    .when()
                    .put(serviceUrl);
        }
        return given(createRequestSpec(requestBody, contentType, includeAuth)).when().put(serviceUrl);
    }


    public Response put(String serviceUrl, String contentType, Object requestBody, Map<String, String> headersMap, boolean includeAuth, boolean log) {
        if (log) {
            return given(createRequestSpec(requestBody, contentType, headersMap, includeAuth)).log().all()
                    .when()
                    .put(serviceUrl);
        }
        return given(createRequestSpec(requestBody, contentType, headersMap, includeAuth)).when().put(serviceUrl);

    }

    public Response patch(String serviceUrl, String contentType, Object requestBody, boolean includeAuth, boolean log) {

        if (log) {
            return given(createRequestSpec(requestBody, contentType, includeAuth)).log().all()
                    .when()
                    .patch(serviceUrl);
        }
        return given(createRequestSpec(requestBody, contentType, includeAuth)).when().put(serviceUrl);
    }


    public Response patch(String serviceUrl, String contentType, Object requestBody, Map<String, String> headersMap, boolean includeAuth, boolean log) {
        if (log) {
            return given(createRequestSpec(requestBody, contentType, headersMap, includeAuth)).log().all()
                    .when()
                    .patch(serviceUrl);
        }
        return given(createRequestSpec(requestBody, contentType, headersMap, includeAuth)).when().put(serviceUrl);

    }

    public Response delete(String serviceUrl, boolean includeAuth, boolean log) {
        if (log) {
            return given(createRequestSpec(includeAuth)).log().all()
                    .when()
                    .delete(serviceUrl);
        }
        return given(createRequestSpec(includeAuth)).when().delete(serviceUrl);
    }

    public String getAccessToken(String serviceUrl,
                               String clientCredentials, String clientId,String clientSecret){
        RestAssured.baseURI = baseUri;
        Response response = given()
                .contentType(ContentType.URLENC)
                .formParam("grant_type", clientCredentials)
                .formParam("client_id", clientId)
                .formParam("client_secret", clientSecret)
                .when()
                .post(serviceUrl);
        response.then()
                .assertThat()
                .statusCode(APIhttpStatus.OK_200.getCode());

        String accessToken = response.getBody().jsonPath().getString("access_token");
        System.out.println("Access Token : "+accessToken);
        return accessToken;
    }
}
