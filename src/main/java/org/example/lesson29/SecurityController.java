package org.example.lesson29;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lesson29")
public class SecurityController {
    @GetMapping("/public")
    public MessageResponse publicEndpoint() {
        return new MessageResponse("public");
    }

    @GetMapping("/profile")
    public MessageResponse profile(Authentication authentication) {
        return new MessageResponse("Hello, " + authentication.getName());
    }

    @GetMapping("/admin")
    public MessageResponse admin() {
        return new MessageResponse("admin");
    }
}
