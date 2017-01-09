package com.cdiscount.poc.oauth.controller;

import com.cdiscount.poc.oauth.security.oauth2.provider.token.store.CustomJwtAccessTokenConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author afillatre@ippon.fr
 * @since 04/01/2017.
 */
@RestController
@DependsOn("checkTokenEndpoint")
public class AuthorizationController {

    @Autowired
    private ResourceServerTokenServices resourceServerTokenServices;

    @Autowired
    private CustomJwtAccessTokenConverter jwtAccessTokenConverter;

    @RequestMapping("/jwt")
    @PreAuthorize("hasRole('RESOURCES')")
    public String getJWT(@RequestParam("token") String accessToken) {
        return jwtAccessTokenConverter.getJWTFromAccessToken(accessToken);
    }

    @RequestMapping({ "/user", "/me" })
    public Map<String, Object> user(@RequestParam("token") String accessToken) {
        Map<String, String> map = new LinkedHashMap<>();
        OAuth2AccessToken oAuth2AccessToken = resourceServerTokenServices.readAccessToken(accessToken);
        return oAuth2AccessToken.getAdditionalInformation();
    }
}
