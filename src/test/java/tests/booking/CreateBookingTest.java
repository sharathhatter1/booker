package tests.booking;

import apiConfigs.ApiPath;
import apipojo.BookingPojo;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.BaseTest;
import utils.Utilities;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import java.io.File;

public class CreateBookingTest extends BaseTest {

    @BeforeClass
    public static void init() {
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .build().log().all();
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

    }

    @Test
    public void createBooking() {
        final File bookSchema = Utilities.getJsonSchema("bookingCreate.json");
        final BookingPojo bookCreate = Utilities.createBookingEntry();

        //System.out.println(testBooking.toString());

        RestAssured.given()
                .body(Utilities.toJson(bookCreate, BookingPojo.class))
                .when()
                .post(ApiPath.booking)
                .then()
                .body(matchesJsonSchema(bookSchema));
        //System.out.println(resp.body().toString());


    }

}
