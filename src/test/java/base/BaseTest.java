package base;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import propConfig.Configuration;
import restClient.RestClient;

import java.util.Properties;

public class BaseTest {


    protected static final String GOREST_ENDPOINT = "/public/v2/users/";
    protected static final String CIRCUIT_ENDPOINT = "api/f1/2017/circuits.json";
    protected static final String REQRES_ENDPOINT = "/api/users/2";
    protected static final String PRODUCT_ENDPOINT = "/products";
    protected static final String FLIGHT_DESTINATIONS = "/v1/shopping/flight-destinations";
    protected static final String AMADEUS_AUTH_ENDPOINT = "/v1/security/oauth2/token";

    protected RestClient restClient;
    protected Properties prop;
    protected Configuration config;
    String baseURI = "https://gorest.co.in";

    //@Parameters({"baseURI"})
    @BeforeTest
    public void setUp() {
        RestAssured.filters(new AllureRestAssured());
        config = new Configuration();
        prop = config.initProp();
        restClient = new RestClient(prop, baseURI);
    }
}
