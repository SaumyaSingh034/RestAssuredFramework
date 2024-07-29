package restClient;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Map;
import java.util.Properties;

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

    private void setRequestContentType(String contentType){
        switch (contentType){
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

    public void restAuthorizationHeaderFlag(){
        isAuthorizationHeaderAdded = false;
    }

    public void addAuthorizationHeader(){
        if(!isAuthorizationHeaderAdded){
            specBuilder.addHeader("Authorization", "Bearer "+prop.getProperty("token"));
            isAuthorizationHeaderAdded = true;
        }
    }

    private RequestSpecification createRequestSpec(boolean includeAuth){
        specBuilder.setBaseUri(baseUri);
        if(includeAuth)
            addAuthorizationHeader();
        return specBuilder.build();
    }

    private RequestSpecification createRequestSpec(Map<String, String> headerMap, boolean includeAuth){
        specBuilder.setBaseUri(baseUri);
        if(includeAuth)
            addAuthorizationHeader();
        if(headerMap!=null)
            specBuilder.addHeaders(headerMap);
        return specBuilder.build();
    }

    private RequestSpecification createRequestSpec(Map<String, String> headerMap, Map<String, Object> querParams, boolean includeAuth){
        specBuilder.setBaseUri(baseUri);
        if(includeAuth)
            addAuthorizationHeader();
        if(headerMap!=null)
            specBuilder.addHeaders(headerMap);
        if(querParams!=null)
            specBuilder.addQueryParams(querParams);
        return specBuilder.build();
    }


}
