import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class AssignmentRestTest {

    @Test
    public void postPutGetHamcrestTest(){
        baseURI = "https://jsonplaceholder.typicode.com";
        Map<String, String> bodyData = new HashMap<>();
        bodyData.put("title", "testmohana");
        bodyData.put("body", "automation");
        bodyData.put("userId", "100");

        given().contentType("application/json").body(bodyData).when().log().all().post("/posts").then().log().status().assertThat().statusCode(201).log().body();

        Map<String, String> bodyPutData = new HashMap<>();
        bodyPutData.put("title", "testmohanalatha");
        bodyPutData.put("body", "automation");
        bodyPutData.put("userId", "100");
        bodyPutData.put("id", "1");

        given().contentType("application/json").body(bodyPutData).when().log().all().put("/posts/1").then().assertThat().statusCode(200).log().body();

        given().when().delete("/posts/1").then().log().all();

        given().when().get("/posts/1").then().log().all()
                .assertThat().statusCode(200)
                .body("id", equalTo(1))
                .body("title", containsString("sunt aut facere"));
    }

    @Test
    public void apiChainTest(){
        RestAssured.baseURI = "https://reqres.in/api";
         given().queryParam("page", "1").when().get("/").then().log().all();

        given().queryParam("page", "1").when().get("/users").then().log().all()
                .assertThat().statusCode(200)
                .body("page", equalTo(1))
                .body("data.email", hasItems("george.bluth@reqres.in", "eve.holt@reqres.in"))
                .body("total", greaterThan(10));

        Map<String, String> bodyData = new HashMap<>();
        bodyData.put("name", "latha");
        bodyData.put("job", "Specalist");

        Response response=  given().contentType("application/json").body(bodyData).when().post("/users").then().contentType(ContentType.JSON).extract().response();
        String userId= response.jsonPath().get("id");
        System.out.println("Response "+ userId);

        Map<String, String> bodyPutData = new HashMap<>();
        bodyPutData.put("name", "testlatha");
        bodyPutData.put("job", "automation");

        given().contentType("application/json").body(bodyPutData).when().log().all().put("/users/"+userId).then().assertThat().statusCode(200).log().body();

        given().when().delete("/users/"+userId).then().log().all().assertThat().statusCode(204);
    }

    @Test
    public void serializationTest(){
        baseURI = "https://reqres.in/api";
        LoginPOJO loginPOJO = new LoginPOJO();
        loginPOJO.setEmail("eve.holt@reqres.in");
        loginPOJO.setPassword("cityslicka");
        Response response= given().contentType("application/json").when().body(loginPOJO).when().log().all().post("/login")
                .then().extract().response();
        Assert.assertEquals(200, response.getStatusCode());
        System.out.println("Token "+response.jsonPath().get("token"));



    }



}
