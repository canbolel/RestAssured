import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

import static io.restassured.RestAssured.given;


public class Request {

    @Before
    public void setup(){
        RestAssured.baseURI = "http://localhost:3000/";

    }

    public static Integer getRequest(){

        Response response =
                given()
                        .log().all()
                        .when()
                        .get("/users/").prettyPeek()
                        .then()
                        .statusCode(200).extract().response();
        //int userId = Integer.parseInt(response.jsonPath().getString("[2].id"));
        int userIdSize = Integer.parseInt(response.jsonPath().getString("id.size()"));

        return userIdSize;
    }

    public static void deleteRequest(int userId){

        Response response =
                given()
                        .log().all()
                        .when()
                        .delete("/users/"+userId+"/").prettyPeek()
                        .then()
                        .statusCode(200).extract().response();
    }

    public static void postRequest(int userId){

        Response response =
                given()
                        .contentType("application/json")
                        .body("{\n" +
                                "    \"id\": "+userId+",\n" +
                                "    \"first_name\": \"Roger\",\n" +
                                "    \"last_name\": \"Bacon\",\n" +
                                "    \"email\": \"rogerbacon12@yahoo.com\"\n" +
                                "}")
                        .log().all()
                        .when()
                        .post("/users").prettyPeek()
                        .then()
                        .statusCode(201).extract().response();

    }

    public static void putRequest() {
        Response response =
                given()
                        .contentType("application/json")
                        .body("{\n" +
                                "    \"id\": "+1+",\n" +
                                "    \"first_name\": \"Rogerrr\",\n" +
                                "    \"last_name\": \"Bacon\",\n" +
                                "    \"email\": \"rogerbacon12@yahoo.com\"\n" +
                                "}")
                        .log().all()
                        .when()
                        .put("/users/"+1+"/").prettyPeek()
                        .then()
                        .statusCode(200).extract().response();
    }

    public static int randomElement(){

        Response response =
                given()
                        .log().all()
                        .when()
                        .get("/users/").prettyPeek()
                        .then()
                        .statusCode(200).extract().response();
        int userIdSize = Integer.parseInt(response.jsonPath().getString("id.size()"));
        ArrayList<Integer> Ids = new ArrayList<Integer>();
        for (int i = 0; i < userIdSize; ++i) {
            Ids.add(Integer.parseInt(response.jsonPath().getString( "["+i+"].id)")));
        }
        Random random = new Random();
        int randomId =Ids.get( random.nextInt(Ids.size()));
        return randomId;
    }

    @Test
    public void requestScenario(){
        int userId = getRequest() + 2;
        int randomId=randomElement();
        getRequest();
        postRequest(userId);
        putRequest();
        deleteRequest(randomId);
        getRequest();
    }
}
