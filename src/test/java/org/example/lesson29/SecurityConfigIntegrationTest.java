package org.example.lesson29;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Lesson29Application.class)
@AutoConfigureMockMvc
class SecurityConfigIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void appliesRouteAuthorizationRules() throws Exception {
        mockMvc.perform(get("/api/lesson29/public"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/lesson29/profile"))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/api/lesson29/profile").with(user("frontend").roles("USER")))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/lesson29/admin").with(user("frontend").roles("USER")))
                .andExpect(status().isForbidden());

        mockMvc.perform(get("/api/lesson29/admin").with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk());
    }

    @Test
    void authenticatesConfiguredUsersWithHttpBasic() throws Exception {
        mockMvc.perform(get("/api/lesson29/profile")
                        .with(httpBasic("frontend", "frontend123")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello, frontend"));

        mockMvc.perform(get("/api/lesson29/admin")
                        .with(httpBasic("admin", "admin123")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("admin"));

        mockMvc.perform(get("/api/lesson29/profile")
                        .with(httpBasic("frontend", "wrong-password")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void allowsViteCorsPreflight() throws Exception {
        mockMvc.perform(options("/api/lesson29/profile")
                        .header(HttpHeaders.ORIGIN, "http://localhost:5173")
                        .header(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD, "GET")
                        .header(HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS,
                                "Authorization, Content-Type"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN,
                        "http://localhost:5173"));
    }
}
