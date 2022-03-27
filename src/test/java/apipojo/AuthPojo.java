package apipojo;

import lombok.Value;
import lombok.Builder;
import com.squareup.moshi.Json;

@Value
@Builder
public class AuthPojo{
    @Json(name = "username")
    String username;

    @Json(name = "password")
    String password;
}

