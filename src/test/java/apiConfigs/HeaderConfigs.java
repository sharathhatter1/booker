package apiConfigs;

import java.util.HashMap;
import java.util.Map;

public class HeaderConfigs {

    public Map<String, String> headerWithToken(){
        Map<String, String> map = new HashMap<>();
        map.put("Content-Type", "application/json");
        return map;
    }

}


