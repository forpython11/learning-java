package org.example.lesson30;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
public class JwtTokenService {
    private final JwtEncoder jwtEncoder;

    public JwtTokenService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public String createToken(Authentication authentication) {
        Instant issuedAt = Instant.now();
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet.Builder claimsBuilder = JwtClaimsSet.builder()
                .issuer("lesson30")
                .issuedAt(issuedAt)
                .claim("scope", scope);

        // DONE 1: 设置 subject 为用户名，并设置 30 分钟后的 expiresAt。
        claimsBuilder
                .subject(authentication.getName())
                .expiresAt(issuedAt.plusSeconds(1800));
        JwtClaimsSet claims = claimsBuilder.build();
        JwsHeader jwsHeader=  JwsHeader.with(MacAlgorithm.HS256).build();
        JwtEncoderParameters parameters = JwtEncoderParameters.from(jwsHeader,claims);
        // DONE 2: 使用 HS256 签名头和 jwtEncoder 编码 claims，返回 token 字符串。
        return jwtEncoder.encode(parameters).getTokenValue();
    }
}
