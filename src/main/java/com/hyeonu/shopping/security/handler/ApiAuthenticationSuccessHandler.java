package com.hyeonu.shopping.security.handler;

import com.hyeonu.shopping.security.config.JwtTokenProvider;
import com.hyeonu.shopping.security.dto.TokenInfo;
import com.hyeonu.shopping.service.RedisUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;


@Component @RequiredArgsConstructor
public class ApiAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisUtils redisUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("로그인 성공");

        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        redisUtils.setData(tokenInfo.getAccessToken(), "ACCESS", jwtTokenProvider.getAccessExpiration());
        response.getWriter().println(tokenInfo);
    }
}
