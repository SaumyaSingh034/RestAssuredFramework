package test;

import base.BaseTest;
import constant.APIhttpStatus;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

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
        
    }
}
