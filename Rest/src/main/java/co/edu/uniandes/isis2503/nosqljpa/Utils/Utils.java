package co.edu.uniandes.isis2503.nosqljpa.Utils;

import com.auth0.jwt.JWT;

import static co.edu.uniandes.isis2503.nosqljpa.auth.AuthenticationFilter.AUTHENTICATION_SCHEME;

public class Utils {

    public static String getUsernameFromToken(String pToken) {
        if (pToken != null) {
            String token = pToken
                    .substring(AUTHENTICATION_SCHEME.length()).trim();
            return JWT.decode(token).getClaim("name").asString();
        } else {
            return "";
        }
    }

}
