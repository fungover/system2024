package org.fungover.system2024.user;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;

@Controller
public class testUserController {
    @QueryMapping
    public String testUserAuth(@AuthenticationPrincipal OAuth2User user) {
        if (user == null) {
            throw new AuthenticationCredentialsNotFoundException("User not authenticated");
        }
        return user.getAttribute("email");
    }
}
