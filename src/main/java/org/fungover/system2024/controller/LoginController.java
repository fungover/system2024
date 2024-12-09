package org.fungover.system2024.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Controller
public class LoginController {

    @GetMapping("/")
    public String index(@AuthenticationPrincipal OAuth2User principal, Model model) {
        if (principal != null) {
            model.addAttribute("name", principal.getAttribute("name"));
        } else {
            model.addAttribute("name", "Guest");
        }
        return "index";
    }
}