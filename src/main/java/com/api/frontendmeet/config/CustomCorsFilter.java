package com.api.frontendmeet.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class CustomCorsFilter extends OncePerRequestFilter {
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
      response.setHeader("Access-Control-Allow-Origin", "*");
      response.setHeader("Access-Control-Allow-Headers", "Authorization, X-Authorization, content-type, accept, accountid");
      response.setHeader("Access-Control-Expose-Headers", "Authorization, X-Authorization, content-type");
      response.setHeader("Access-Control-Allow-Methods", "GET, POST, PATCH, OPTIONS, PUT, DELETE, HEAD");
      if ("OPTIONS".equals(request.getMethod())) {
          response.setStatus(HttpServletResponse.SC_OK);
      } else {
          filterChain.doFilter(request, response);
      }
  }

}