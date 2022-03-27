package utils;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import apipojo.BookingPojo;
import com.squareup.moshi.Moshi;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.RandomUtils;

import static io.restassured.RestAssured.given;



public class Utilities {

    private static final Moshi MOSHI = new Moshi.Builder().build();

    public static String getDate(String format, int dateDiffFromCurrentDate) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) + dateDiffFromCurrentDate);
        DateFormat df1 = new SimpleDateFormat(format);
        String time1 = df1.format(cal.getTime());
        return time1;
    }

    public static <T> String toJson(T object, Class<T> type) {
        return MOSHI
                .adapter(type)
                .lenient()
                .toJson(object);
    }

    public static String getToken() {
        RequestSpecification requestSpec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBody("{ \"username\" : \"admin\", \"password\" : \"password123\"}")
                .build();

        return given(requestSpec).post("https://restful-booker.herokuapp.com/auth").path("token");
    }

    public static BookingPojo createBookingEntry() {
        return BookingPojo.builder()
                .firstname("Sharath")
                .lastname("Hatter")
                .totalprice(100)
                .depositpaid(true)
                .bookingdates(BookingPojo.CheckInOutDate.builder()
                        .checkin(Utilities.getDate("yyyy-MM-dd", 2))
                        .checkout(Utilities.getDate("yyyy-MM-dd", 4))
                        .build())
                .additionalneeds("Spa")
                .build();
    }

    public static File getJsonSchema(String schemaPath) {
        ClassLoader classLoader = BaseTest.class.getClassLoader();
        return new File(Objects.requireNonNull(classLoader.getResource(schemaPath)).getFile());
    }

    public static Integer getRandomBookingID() {
        Response resp = given().contentType(ContentType.JSON)
                .get()
                .then().extract().response();
        List<Map<String, Integer>> bookingID = resp.jsonPath().getList("$");
        return bookingID.get(RandomUtils.nextInt(0, bookingID.size())).get("bookingid");
    }



}
