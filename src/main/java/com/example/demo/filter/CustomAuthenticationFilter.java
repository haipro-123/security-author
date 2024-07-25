package com.example.demo.filter;

import com.example.demo.util.GenerateTokenHMAC;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * filter parse token
 */
@Component
public class CustomAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private GenerateTokenHMAC generateTokenHMAC;
    @Value("${token.user.id}")
    private String TOKEN_USER_ID;
    @Value("${token.user.role}")
    private String TOKEN_USER_ROLE;
    @Value("${token.name}")
    private String TOKEN_NAME;
    // filter catch token
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader(TOKEN_NAME);
        if(authorization != null && !authorization.isEmpty()){
            Claims claims = generateTokenHMAC.parseJwt(authorization).getBody();
            if(claims.getExpiration().after(new Date())){
                String idUser = claims.get(TOKEN_USER_ID).toString();
                String role = claims.get(TOKEN_USER_ROLE).toString();
                List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("ROLE_"+role));
                System.out.println(authorities.toString());
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(idUser, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
