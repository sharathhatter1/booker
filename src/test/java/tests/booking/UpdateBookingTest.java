package tests.booking;

import apipojo.BookingPojo;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.BaseTest;
import utils.Utilities;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.hamcrest.Matchers.hasToString;
import static utils.Utilities.getToken;

import java.io.File;

public class UpdateBookingTest extends BaseTest {

    private final File bookingListSchema = Utilities.getJsonSchema("bookingList.json");
    private final File bookingExistSchema = Utilities.getJsonSchema("bookingExist.json");
    private final BookingPojo bookCreate = Utilities.createBookingEntry();

    @BeforeClass
    public static void setup() {
        RestAssured.basePath = "/booking";
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .build();
        RestAssured.responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(HttpStatus.SC_OK)
                .expectContentType(ContentType.JSON)
                .build();
    }

    @Test
    public void getAllBookings() {
        RestAssured.given()
                .when()
                .get()
                .then()
                .body(matchesJsonSchema(bookingListSchema));
    }

    @Test
    public void getBooking() {
        String bookingIDpath = "/" + Utilities.getRandomBookingID();

        RestAssured.given()
                .when()
                .get(bookingIDpath)
                .then()
                .body(matchesJsonSchema(bookingExistSchema));
    }

    @Test
    public void updateBooking() {
        String bookingIDpath = "/" + Utilities.getRandomBookingID();

        RestAssured.given()
                .cookie("token", getToken())
                .body(Utilities.toJson(bookCreate, BookingPojo.class))
                .when()
                .put(bookingIDpath)
                .then()
                .body("firstname", hasToString(bookCreate.getFirstname()));
    }

    @Test
    public void patchBooking() {
        String bookingIDpath = "/" + Utilities.getRandomBookingID();

        RestAssured.given()
                .cookie("token", getToken())
                .body("{ \"firstname\" : \"Sharath\", \"lastname\" : \"Rudramuniyappa\"}")
                .when()
                .patch(bookingIDpath)
                .then()
                .body("firstname", hasToString("Sharath"));
    }

    @Test
    public void patchWithNoToken() {
        String bookingIDpath = "/" + Utilities.getRandomBookingID();

        RestAssured.given()
                .accept(ContentType.JSON)
                .body("{ \"firstname\" : \"Sharath\", \"lastname\" : \"Hatter\"}")
                .when()
                .patch(bookingIDpath)
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @Test
    public void updateWithNoToken() {
        String bookingIDpath = "/" + Utilities.getRandomBookingID();

        RestAssured.given()
                .body(Utilities.toJson(bookCreate, BookingPojo.class))
                .when()
                .put(bookingIDpath)
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }


}

