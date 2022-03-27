package utils;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import io.restassured.RestAssured;



@Listeners(ExtentReportListener.class)
public class BaseTest extends ExtentReportListener{

    /*
     * This is base testclass which will be called before every test class
     * It sets the base uri
     */

    @BeforeClass
    public void baseTest() {
        //System.out.println("Base url -->"+FileAndEnv.envAndFile().get("serverURl"));
        RestAssured.baseURI = FileAndEnv.envAndFile().get("serverURl").trim();
    }


    @AfterClass
    public static void after() {
        RestAssured.basePath = "";
        RestAssured.requestSpecification = null;
        RestAssured.responseSpecification = null;
    }

}