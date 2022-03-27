package tests.authenticate;

import apiConfigs.ApiPath;
import apipojo.AuthPojo;
import com.relevantcodes.extentreports.LogStatus;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;
import utils.BaseTest;
import utils.Utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static org.hamcrest.Matchers.hasKey;



public class AuthSuccessTest extends BaseTest {

    public static Properties propMain = new Properties();

    @BeforeClass
    public static void init() {
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();
        RestAssured.responseSpecification = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(HttpStatus.SC_OK)
                .build();
    }

    @Test
    public void hasToken() throws IOException {
        FileInputStream fisQA = new FileInputStream(System.getProperty("user.dir") + "/inputs/prod.properties");
        propMain.load(fisQA);

        AuthPojo authpojo = AuthPojo.builder()
                .username(propMain.getProperty("userName"))
                .password(propMain.getProperty("password"))
                .build();

      RestAssured.given()
                .body(Utilities.toJson(authpojo, AuthPojo.class))
                .when()
                .post(ApiPath.auth)
                .then()
                .body("$", hasKey("token"));

        test.log(LogStatus.INFO, "Auth api verified" );


        //System.out.println(resp.getBody().asString());
    }
}
