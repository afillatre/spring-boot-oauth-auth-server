package com.cdiscount.poc.oauth.security.oauth2.provider.token.store;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by afillatre on 09/01/2017.
 */
public class CustomJwtAccessTokenConverter extends JwtAccessTokenConverter {

    private static final Map<String, String> jwtToAccessTokenMap = new HashMap<>(10);

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        OAuth2AccessToken oAuth2AccessToken = super.enhance(accessToken, authentication);

        String jwt = oAuth2AccessToken.getValue();
        String accessTokenStr = String.valueOf(decode(jwt).get("jti"));
        jwtToAccessTokenMap.put(accessTokenStr, jwt);
        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setValue(accessTokenStr);

        return oAuth2AccessToken;
    }

    public String getJWTFromAccessToken(String accessToken) {
        return jwtToAccessTokenMap.getOrDefault(accessToken, null);
    }

    @Override
    protected String encode(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        accessToken.getAdditionalInformation().put("user_email", "afillatre@ippon.fr");
        return super.encode(accessToken, authentication);
    }

    @Override
    protected Map<String, Object> decode(String token) {
        return super.decode(jwtToAccessTokenMap.getOrDefault(token, token));
    }
}
