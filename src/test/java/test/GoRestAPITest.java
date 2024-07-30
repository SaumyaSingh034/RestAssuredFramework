package test;

import base.BaseTest;
import constant.APIhttpStatus;
import org.codehaus.groovy.util.StringUtil;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pojo.User;
import utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;

public class GoRestAPITest extends BaseTest {

    @Test
    public void getAllUserTest() {
        restClient.get(GOREST_ENDPOINT, true, true)
                .then().log().all()
                .assertThat().statusCode(APIhttpStatus.OK_200.getCode());
    }

    @Test
    public void getUserTest() {
        restClient.get(GOREST_ENDPOINT + 7225651, true, true)
                .then().log().all()
                .assertThat().statusCode(APIhttpStatus.OK_200.getCode());
    }

    @Test
    public void getUserWithQueryTest() {
        Map<String, Object> queryParam = new HashMap<>();
        queryParam.put("name", "Naveen");
        queryParam.put("status", "active");
        restClient.get(GOREST_ENDPOINT, queryParam, null, true, true)
                .then().log().all()
                .assertThat().statusCode(APIhttpStatus.OK_200.getCode());

    }

    @DataProvider
    public Object[][] getData() {
        return new Object[][]{
                {"Rahul", "male", "active"},
                {"saumya", "female", "active"},
                {"palak", "female", "inactive"},
                {"piyush", "male", "active"}
        };
    }

    @Test(dataProvider = "getData")
    public void createUserDataTest(String name, String gender, String status) {
        User user = new User(name, StringUtils.getRandomEmailId(), gender, status);
        Integer userId = restClient.post(GOREST_ENDPOINT,"json",user, true, true)
                .then().log().all()
                .assertThat().statusCode(APIhttpStatus.CREATED_201.getCode())
                .and()
                .extract().path("id");
        System.out.println("User Id : "+userId);

        restClient.get(GOREST_ENDPOINT+userId,true, true)
                .then().log().all()
                .assertThat().statusCode(APIhttpStatus.OK_200.getCode());

    }

    @Test
    public void createUserWithBuiderTest() {
        User user  = User.builder().name("NaveenK").email(StringUtils.getRandomEmailId()).gender("male").status("active").build();

        Integer userId = restClient.post(GOREST_ENDPOINT, "json", user, true, true)
                .then().log().all()
                .assertThat()
                .statusCode(APIhttpStatus.CREATED_201.getCode())
                .and()
                .extract().path("id");

        System.out.println("user id: " + userId);

        restClient.get(GOREST_ENDPOINT+userId, true, true)
                .then().log().all()
                .assertThat().statusCode(APIhttpStatus.OK_200.getCode());

    }


    @Test
    public void updateUserTest() {
        //1. POST
        User user = new User("Naveen", StringUtils.getRandomEmailId(), "male", "active");
        Integer userId = restClient.post(GOREST_ENDPOINT, "json", user, true, true)
                .then().log().all(true)
                .assertThat()
                .statusCode(APIhttpStatus.CREATED_201.getCode())
                .and()
                .extract().path("id");

        System.out.println("user id: " + userId);

        //2. PUT
        user.setName("Naveen Automation");
        user.setStatus("inactive");
        restClient.put(GOREST_ENDPOINT+userId, "json", user, true, true)
                .then().log().all(true)
                .assertThat()
                .statusCode(APIhttpStatus.OK_200.getCode())
                .body("id", equalTo(userId));


        //3. GET
        restClient.get(GOREST_ENDPOINT+userId, true, true)
                .then().log().all(true)
                .assertThat().statusCode(APIhttpStatus.OK_200.getCode());

    }


    @Test
    public void deleteUserTest() {
        //1. POST
        User user = new User("Naveen", StringUtils.getRandomEmailId(), "male", "active");
        Integer userId = restClient.post(GOREST_ENDPOINT, "json", user, true, true)
                .then().log().all(true)
                .assertThat()
                .statusCode(APIhttpStatus.CREATED_201.getCode())
                .and()
                .extract().path("id");

        System.out.println("user id: " + userId);

        //2. DELETE
        restClient.delete(GOREST_ENDPOINT+userId, true, true)
                .then().log().all(true)
                .assertThat()
                .statusCode(APIhttpStatus.NO_CONTENT_204.getCode());


        //3. GET
        restClient.get(GOREST_ENDPOINT+userId, true, true)
                .then().log().all(true)
                .assertThat().statusCode(APIhttpStatus.NOT_FOUND_404.getCode()).body("message", equalTo("Resource not found"));

    }
}
