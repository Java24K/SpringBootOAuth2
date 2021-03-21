package com.oauth2.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oauth2.example.domain.UserInfo;

import java.security.Principal;

/**
 * @author zhoufeng
 */
@RestController
@RequestMapping("/oauth2")
public class ResourceController {

    @Autowired
    private ClientDetailsServiceConfigurer clientDetailsServiceConfigurer;

    @GetMapping("/user-name")
    public String userName(Principal principal) {
        return "User name is " + principal.getName();
    }

    @RequestMapping("/user-open-id")
    public String userOpenId(Principal principal) {
        return "User openId is " + principal.getName();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping("/say-hi")
    public String sayHi(Principal principal) {
        return "Hi~ " + principal.getName();
    }

}