package tests.authenticate;

import apiConfigs.ApiPath;
import apipojo.AuthPojo;
import com.relevantcodes.extentreports.LogStatus;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import utils.BaseTest;


import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.Utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class AuthFailureTest extends BaseTest {

    public static Properties propMain = new Properties();
    public static SoftAssert softAssert;

    @BeforeClass
    public static void init() throws IOException {
        FileInputStream fisQA = new FileInputStream(System.getProperty("user.dir") + "/inputs/prod.properties");
        propMain.load(fisQA);
        softAssert = new SoftAssert();
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();
    }


    @Test
    public void LoginWithInvalidCred() {
        AuthPojo authPojo = AuthPojo.builder()
                .username("invaliddata")
                .password("invaliddata")
                .build();

        Response resp =  RestAssured.given()
                .body(Utilities.toJson(authPojo, AuthPojo.class))
                .when()
                .post(ApiPath.auth);
//        System.out.println(resp.getBody().asString());
       String data = resp.getBody().path("reason").toString();
//        System.out.println((data));
        softAssert.assertEquals(data, "Bad credentials");
    }

    @Test
     public void LoginWithNoCred() {
        Response resp = RestAssured.given()
                .when()
                .post(ApiPath.auth);
        String data = resp.getBody().path("reason").toString();
        softAssert.assertEquals(data, "Bad credentials");

    }

    @Test
    public void LoginWithInvalidUserName() {
        AuthPojo authPojo = AuthPojo.builder()
                .username("invalidData")
                .password("password123")
                .build();

        Response resp = RestAssured.given()
                .body(Utilities.toJson(authPojo, AuthPojo.class))
                .when()
                .post(ApiPath.auth);
        String data = resp.getBody().path("reason").toString();
        softAssert.assertEquals(data, "Bad credentials");

    }

    @Test
    public void LoginWithInvalidPassword() {
        AuthPojo authPojo = AuthPojo.builder()
                .username("admin")
                .password("invaliddata")
                .build();

        Response resp = RestAssured.given()
                .body(Utilities.toJson(authPojo, AuthPojo.class))
                .when()
                .post(ApiPath.auth);
        String data = resp.getBody().path("reason").toString();
        softAssert.assertEquals(data, "Bad credentials");

    }

    @Test
    public void LoginWithNoUserName() {

        AuthPojo authPojo = AuthPojo.builder()
                .password(propMain.getProperty("password"))
                .build();

        Response resp = RestAssured.given()
                .body(Utilities.toJson(authPojo, AuthPojo.class))
                .when()
                .post(ApiPath.auth);
        String data = resp.getBody().path("reason").toString();
        softAssert.assertEquals(data, "Bad credentials");

    }

    @Test
    public void LoginWithNoPassword() {
        AuthPojo authPojo = AuthPojo.builder()
                .username(propMain.getProperty("userName"))
                .build();

        Response resp = RestAssured.given()
                .body(Utilities.toJson(authPojo, AuthPojo.class))
                .when()
                .post(ApiPath.auth);
        String data = resp.getBody().path("reason").toString();
        softAssert.assertEquals(data, "Bad credentials");

    }

}
