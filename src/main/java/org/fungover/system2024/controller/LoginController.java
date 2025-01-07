package org.fungover.system2024.controller;


import org.fungover.system2024.user.UserService;
import org.fungover.system2024.user.entity.User;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Controller
public class LoginController {
        @Autowired
        private UserService userService;


   private final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @GetMapping("/")
    public String index(@AuthenticationPrincipal OAuth2User principal, Model model) {
        if (principal != null) {
            Integer userId = principal.getAttribute("id");
            model.addAttribute("name", principal.getAttribute("name"));
            logger.info("User '{}' logged in", userId);

            String userName = principal.getName();
            if(userName != null && userName.equals("Agent-Smith")) {
                logger.info("They are in");
            }

            // Check if user already exist in database, then if-statements to check null-values
                if (!userService.existsById(userId)) {
                        User user = new User();

                        if(principal.getAttribute("email") !=null) {
                                user.setEmail(principal.getAttribute("email"));
                        } else {
                                user.setEmail("unknown@example.something");
                        }

                        if(principal.getAttribute("name") != null) {
                                user.setFirst_name(principal.getAttribute("name"));
                        } else {
                                user.setFirst_name("John");
                        }

                        if (principal.getAttribute("name") != null) {
                                user.setLast_name(principal.getAttribute("name"));
                        } else {
                                user.setLast_name("Doe");
                        }

                        if(principal.getAttribute("password") != null) {
                                user.setPassword(principal.getAttribute("password"));
                        } else {
                                user.setPassword("no password from this kid");
                        }

                        userService.save(user);

                        logger.info("New user created with ID '{}'", userId);
                } else {
                        logger.info("User '{}' already exists in the database", userId);
                }

        } else {
            model.addAttribute("name", "Guest");
        }
        return "index";
    }
}