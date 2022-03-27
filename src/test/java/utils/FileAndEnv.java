package utils;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class FileAndEnv {

    public static Map<String, String> fileandenv = new HashMap<String, String>();
    public static Properties propMain = new Properties();

    public static Map<String, String> envAndFile() {
        try {
            FileInputStream fisQA = new FileInputStream(System.getProperty("user.dir") + "/inputs/prod.properties");
            propMain.load(fisQA);
            fileandenv.put("serverURl", propMain.getProperty("serverURl"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            // TODO: handle exception
        }
        return fileandenv;
    }


    public static Map<String, String> getConfigReader(){
        if(fileandenv == null) {
            fileandenv = envAndFile();
        }
        return fileandenv;

    }

}
