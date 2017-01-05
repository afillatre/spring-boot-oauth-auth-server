package com.cdiscount.poc.oauth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author afillatre@ippon.fr
 * @since 04/01/2017.
 */
@RestController
public class AuthorizationController {

    @RequestMapping("/code")
    public String getCode(HttpServletRequest request) {
        return request.getParameter("code");
    }

    @RequestMapping({ "/user", "/me" })
    public Map<String, String> user(Principal principal) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("name", principal.getName());
        return map;
    }
}
