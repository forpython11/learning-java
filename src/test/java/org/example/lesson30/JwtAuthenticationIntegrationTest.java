package org.example.lesson30;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Lesson30Application.class)
@AutoConfigureMockMvc
class JwtAuthenticationIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private JwtDecoder jwtDecoder;

    @Test
    void loginReturnsJwtWithExpectedClaims() throws Exception {
        String token = loginAndGetToken();
        Jwt jwt = jwtDecoder.decode(token);

        assertEquals("frontend", jwt.getSubject());
        assertEquals("lesson30", jwt.getClaimAsString("iss"));
        assertNotNull(jwt.getIssuedAt());
        assertNotNull(jwt.getExpiresAt());
        assertEquals(1800, Duration.between(
                jwt.getIssuedAt(),
                jwt.getExpiresAt()
        ).getSeconds());
    }

    @Test
    void bearerTokenCarriesIdentityToProfile() throws Exception {
        String token = loginAndGetToken();

        mockMvc.perform(get("/api/lesson30/profile")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("frontend"));
    }

    @Test
    void rejectsInvalidCredentialsAndTokens() throws Exception {
        mockMvc.perform(post("/api/lesson30/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"frontend\",\"password\":\"wrong\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value("INVALID_CREDENTIALS"));

        mockMvc.perform(get("/api/lesson30/profile"))
                .andExpect(status().isUnauthorized());

        String token = loginAndGetToken();
        mockMvc.perform(get("/api/lesson30/profile")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token + "x"))
                .andExpect(status().isUnauthorized());
    }

    private String loginAndGetToken() throws Exception {
        MvcResult result = mockMvc.perform(post("/api/lesson30/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"frontend\",\"password\":\"frontend123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isString())
                .andReturn();

        TokenResponse response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                TokenResponse.class
        );
        return response.token();
    }
}
