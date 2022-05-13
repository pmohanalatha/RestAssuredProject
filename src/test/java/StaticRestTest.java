import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import io.restassured.RestAssured;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class StaticRestTest {
    @Test
    public void testRestStatic(){
//        RestAssured.baseURI = "https://reqres.in/api/users";
       // given().queryParam("page", "1").when().get("/").then().log().all();

        RestAssured.baseURI = "https://reqres.in/api";
//        given().queryParam("page", "1").when().get("/users").then().log().all();
        given().queryParam("page", "1").when().get("/users").then().log().all()
                .assertThat().statusCode(200)
                .body("page", equalTo(1))
                .body("data.first_name", hasItems("George", "Tracey"))
                .body("total", greaterThan(10));


        given().when().get("/users/3").then().log().all();

        Map<String, String> bodyData = new HashMap<>();
        bodyData.put("email", "sdsd@sfds.com");
        bodyData.put("password", "sdsd");

        given().contentType("application/json").body(bodyData).when().post("/register").then().log().body();

    }


    @Test
    public void secondTest(){
        baseURI = "https://reqres.in/api";
        given().when().get("/users/23").then().assertThat().statusCode(404);
        Map<String, String> bodyData = new HashMap<>();
        bodyData.put("name", "testmohana");
        bodyData.put("job", "automation");

        given().body(bodyData).when().log().all().post("/users").then().log().status().assertThat().statusCode(201).log().body();

        Map<String, String> bodyPutData = new HashMap<>();
        bodyPutData.put("name", "testmohanalatha");
        bodyPutData.put("job", "automation");

        given().body(bodyPutData).when().log().all().put("/users/562").then().assertThat().statusCode(200).log().body();

        given().when().delete("/users/562").then().log().all();
    }

    @Test
    public void thirdTest(){
        baseURI = "https://reqres.in/api";
        UserPOJO userPOJO = new UserPOJO();
        userPOJO.setUserName("Eamani");
        userPOJO.setJob("Agriculture");
        System.out.println("POJO "+ userPOJO.toString());
        given().body(userPOJO).when().log().all().post("/users").then().log().status().assertThat().statusCode(201).log().body();

    }



}
