package tests.booking;

import apipojo.BookingPojo;
import utils.BaseTest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.Utilities;

import static utils.Utilities.getToken;

public class DeleteBookingTest extends BaseTest {

    @BeforeClass
    public static void init() {
        RestAssured.basePath = "booking";
    }

    @Test
    public void deleteeBooking() {
        BookingPojo bookCreate = Utilities.createBookingEntry();
        Integer bookingID = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(Utilities.toJson(bookCreate, BookingPojo.class))
                .post()
                .then()
                .extract().response().path("bookingid");

        System.out.println((bookingID));

        RestAssured.given()
                .contentType(ContentType.JSON)
                .cookie("token", getToken())
                .delete("/" + bookingID)
                .then()
                .statusCode(HttpStatus.SC_CREATED);

        RestAssured.given()
                .contentType(ContentType.TEXT)
                .when()
                .get("/" + bookingID)
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }
}