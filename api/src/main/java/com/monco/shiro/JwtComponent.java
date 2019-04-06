package com.monco.shiro;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

/**
 * JwtComponent
 *
 * @author Lijin
 * @version 1.0.0
 */
public class JwtComponent {

    private static final String USERNAME = "username";
    private static final String VERSION = "version";

    public static boolean verify(String token, String username, Long version, String secret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret + 0);
            JWTVerifier verifier = JWT.require(algorithm).withClaim(USERNAME, username).withClaim(VERSION, 0).build();
            verifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(USERNAME).asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    public static Long getVersion(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(VERSION).asLong();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    public static String sign(String username, Long version, String secret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret + 0);
            return JWT.create().withClaim(USERNAME, username).withClaim(VERSION, 0).sign(algorithm);
        } catch (Exception e) {
            return null;
        }
    }

}