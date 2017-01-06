package com.cdiscount.poc.oauth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.endpoint.CheckTokenEndpoint;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author afillatre@ippon.fr
 * @since 04/01/2017.
 */
@RestController
public class AuthorizationController {

    @Autowired
//    private ResourceServerTokenServices resourceServerTokenServices;
    private CheckTokenEndpoint checkTokenEndpoint;

    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @RequestMapping("/jwt")
    public OAuth2AccessToken user(@RequestParam("token") String accessToken) {

/*
       OAuth2AccessToken token = resourceServerTokenServices.readAccessToken(accessToken);
        if (token == null) {
            throw new InvalidTokenException("Token was not recognised");
        }

        if (token.isExpired()) {
            throw new InvalidTokenException("Token has expired");
        }

        OAuth2Authentication authentication = resourceServerTokenServices.loadAuthentication(token.getValue());
        return jwtAccessTokenConverter.enhance(token, authentication);
        */
        Map<String, ?> stringMap = checkTokenEndpoint.checkToken(accessToken);
        return null;
    }

    @RequestMapping({ "/user", "/me" })
    //@PreAuthorize("hasRole('RESOURCES')")
    public Map<String, String> user(Principal principal) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("name", principal.getName());
        return map;
    }
}
