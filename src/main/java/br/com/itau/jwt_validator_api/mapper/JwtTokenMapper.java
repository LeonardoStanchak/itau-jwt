package br.com.itau.jwt_validator_api.mapper;

import br.com.itau.jwt_validator_api.domain.JwtToken;

import java.util.Map;

public class JwtTokenMapper {

    public static JwtToken fromClaims(Map<String, Object> claims) {
        return new JwtToken(
                String.valueOf(claims.get("Name")),
                String.valueOf(claims.get("Role")),
                String.valueOf(claims.get("Seed"))
        );
    }
}
