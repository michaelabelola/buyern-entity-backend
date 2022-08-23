package com.buyern.buyern.Services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.buyern.buyern.Configs.Authorities;
import com.buyern.buyern.Configs.CustomAuthority;
import com.buyern.buyern.Models.User.UserAuth;
import com.buyern.buyern.Models.User.UserAuthSession;
import com.buyern.buyern.Repositories.UserAuthRepository;
import com.buyern.buyern.Repositories.UserSessionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Data
public class UserSessionService {
    private static final Logger logger = LoggerFactory.getLogger(UserSessionService.class);
    @Value("${jwt.secret}")
    private String SECRET;
    //    in secs
    private static final Long EXPIRATION_TIME = 3600300L;
    private static final String AUTH_TOKEN_PREFIX = "Bearer ";
    private static final String AUTH_HEADER_STRING = "Authorization";
    private final UserSessionRepository userSessionRepository;
    private final UserAuthRepository userAuthRepository;

    public void startNewUserSession(UserAuth userAuth, HttpServletResponse response, boolean persist) {
        createSession(userAuth, response, persist, null);
    }

    private void createSession(UserAuth userAuth, HttpServletResponse response, boolean persist, @Nullable String previousSessionId) {
        String jwtId = UUID.randomUUID().toString();
        String sessionId = UUID.randomUUID().toString();
        String token = JWT.create()
                .withPayload(new ObjectMapper().convertValue(userAuth.getAuthorities(), new TypeReference<>() {
                }))
                .withSubject(String.valueOf(userAuth.getUId()))
                .withJWTId(sessionId)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SECRET.getBytes()));
        logger.info(token);
        response.addHeader(AUTH_HEADER_STRING, String.format("%s%s", AUTH_TOKEN_PREFIX, token));
        if (persist)
            saveSessionToStorage(userAuth, jwtId, sessionId);
    }

    public void refreshUserSession(String userId, Claim sessionId, HttpServletResponse response, boolean persist) {
        logger.info("Token expired. refresh token");
        UserAuth userAuth = userAuthRepository.findByuId(userId).orElseThrow(() -> new AuthenticationCredentialsNotFoundException("Cannot Refresh session. Invalid user id in jwt"));
        startNewUserSession(userAuth, response, persist);
    }

    private void saveSessionToStorage(UserAuth userAuth, String jwtId, String sessionId) {
//        List<CustomAuthority> authorities1 = new ObjectMapper().convertValue(userAuth.getAuthorities(), new TypeReference<List<CustomAuthority>>() {});
        List<CustomAuthority> authorities = Authorities.from(List.of("READ", "WRITE", "BLAH", "BLAH"));
        UserAuthSession userSession = new UserAuthSession();
        userSession.setId(userAuth.getUId());
        userSession.setAuthorities(authorities);
        userSession.setJwtId(sessionId);
        userSession.setDisabled(userAuth.isDisabled());
        userSession.setExpired(userAuth.isExpired());
        userSession.setLocked(userAuth.isLocked());
        userSession.setVerified(userAuth.isVerified());
        userSession.setCredentialExpired(userAuth.isCredentialExpired());
        userSession.setLoginTime(new Date());
        try {
            logger.error(new ObjectMapper().writeValueAsString(userSession));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        userSessionRepository.save(userSession);
    }

    public void endUserSession() {

    }

    public void deleteUserSession() {

    }

    public void validateUserSession() {

    }
    public UserAuthSession getSession(String userId) {
        return userSessionRepository.findById(userId).orElseThrow(() -> new SessionAuthenticationException("User Session Not found: try logging"));
    }

}
