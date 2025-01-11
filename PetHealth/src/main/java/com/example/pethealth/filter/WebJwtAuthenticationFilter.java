package com.example.pethealth.filter;


import com.example.pethealth.components.JwtTokenUtil;
import com.example.pethealth.model.User;

import com.example.pethealth.service.auth.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.springframework.data.util.Pair;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class WebJwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
            if(isBypassToken(request)){
            filterChain.doFilter(request,response);
            return;
        }
        final String authHeader = request.getHeader("Authorization");
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            return;
        }
        final String token = authHeader.substring(7);
        final String username= jwtTokenUtil.extractUsername(token);
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            User user = (User) userService.loadUserByUsername(username);
            if(jwtTokenUtil.isValid(token, user)){
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                user,
                                null,
                                user.getAuthorities()
                        );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request,response);
    }

    private boolean isBypassToken(@NonNull HttpServletRequest request){
        final List<Pair<String,String>> bypassTokens = Arrays.asList(
                Pair.of("/api/auth/login", "POST"),
                Pair.of("/api/auth/register", "POST"),
                Pair.of("/api/doctor/getAppointmentAll", "GET"),
                Pair.of("/uploads/","GET"),
                Pair.of("/uploads", "GET"),
                Pair.of("/uploads/","POST"),
                Pair.of("/api/home", "GET"),
                Pair.of("/api/doctor/createAppointment", "POST"),
                Pair.of("/api/serviceMedical","GET"),
                Pair.of("api/typeQuestion/getAllTypeQuestion","GET"),
                Pair.of("api/comment/getAllCommentQuestion/","GET"),
                Pair.of("api/typePost/getAllTypePost","GET"),
                Pair.of("api/post/getAllPost", "GET"),
                Pair.of("api/post/getPostDetails/", "GET")
        );
        for(Pair<String , String> bypassToken : bypassTokens){
            if(request.getServletPath().contains(bypassToken.getFirst()) &&
                    request.getMethod().equals(bypassToken.getSecond())){
                return true;
            }
        }
        return false;
    }
}