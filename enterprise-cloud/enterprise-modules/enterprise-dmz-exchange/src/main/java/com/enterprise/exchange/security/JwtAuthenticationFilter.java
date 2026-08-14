package com.enterprise.exchange.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final TokenManager tokens;

    public JwtAuthenticationFilter(TokenManager tokens) {
        this.tokens = tokens;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest r, HttpServletResponse s, FilterChain c) throws ServletException, IOException {
        String h = r.getHeader("Authorization");
        if (h != null && h.startsWith("Bearer ")) {
            String subject = tokens.getSubject(h.substring(7));
            if (subject != null)
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(subject, null, AuthorityUtils.NO_AUTHORITIES));
        }
        c.doFilter(r, s);
    }
}
