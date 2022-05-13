import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RestAssuredTest {

    @Test
    public void firstTest(){
        RestAssured.baseURI= "https://jsonplaceholder.typicode.com";
//        RequestSpecification requestSpecification = RestAssured.given();
//        Response response = requestSpecification.request(Method.GET, "/todos/1");
        Response response = RestAssured.given().when().get("/todos/1");
        System.out.println("Status Code" +response.getStatusCode());
        System.out.println("Response body" +response.getBody().asPrettyString());
        System.out.println("Headers" +response.getHeaders());
        Assert.assertEquals(response.getStatusCode(), 200);
    }


}
