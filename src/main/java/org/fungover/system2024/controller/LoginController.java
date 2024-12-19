package org.fungover.system2024.controller;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Controller
public class LoginController {

//   private final Logger logger = LoggerFactory.getLogger(LoginController.class);
//    @GetMapping("/")
//    public String index(@AuthenticationPrincipal OAuth2User principal, Model model) {
//        if (principal != null) {
//            Integer userId = principal.getAttribute("id");
//            model.addAttribute("name", principal.getAttribute("name"));
//            logger.info("User '{}' logged in", userId);
//
//            String userName = principal.getName();
//            if(userName != null && userName.equals("Agent-Smith")) {
//                logger.info("They are in");
//            }
//
//
//        } else {
//            model.addAttribute("name", "Guest");
//        }
//        return "index";
//    }
}
