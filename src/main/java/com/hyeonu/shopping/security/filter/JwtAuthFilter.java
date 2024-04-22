package com.hyeonu.shopping.security.filter;

import com.hyeonu.shopping.dto.CustomUserDetails;
import com.hyeonu.shopping.security.config.JwtTokenProvider;
import com.hyeonu.shopping.security.service.CustomUserDetailsService;
import com.hyeonu.shopping.service.RedisUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtProvider;
    private final CustomUserDetailsService customUserDetailsService;
    private final RedisUtils redisUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);

            // jwt 유효성 검증
            if (jwtProvider.validateToken(token)) {
                System.out.println("유효성 검증");
                try {
                    if(redisUtils.getData(token) == null) {
                        throw new AccessDeniedException("토큰 만료");
                    }
                    String username = jwtProvider.getUsername(token);
                    CustomUserDetails users = customUserDetailsService.loadUserByUsername(username);
                    if (users != null) {
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                                new UsernamePasswordAuthenticationToken(users, null, users.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                        System.out.println("검증완료");
                    }
                }catch (Exception e) {
                    log.info(e.toString());
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
