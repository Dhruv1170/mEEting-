package com.api.frontendmeet.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class SimpleAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	@Override
    public Authentication attemptAuthentication(
      HttpServletRequest request, 
      HttpServletResponse response) 
        throws AuthenticationException {

        UsernamePasswordAuthenticationToken authRequest
          = getAuthRequest(request);
        setDetails(request, authRequest);
        
        return this.getAuthenticationManager()
          .authenticate(authRequest);
    }

    private UsernamePasswordAuthenticationToken getAuthRequest(
      HttpServletRequest request) {
 
        String username = obtainUsername(request);
        String password = obtainPassword(request);
        String role = obtainRole(request);
        
        System.out.println("role ========> "+role);

        String usernameDomain = String.format("%s%s%s", username.trim(), 
          String.valueOf(Character.LINE_SEPARATOR), role);
        
        System.out.println("usernameDomain ====> "+usernameDomain);
        return new UsernamePasswordAuthenticationToken(
          usernameDomain, password);
    }
    
    protected String obtainRole(HttpServletRequest request) {
		return request.getParameter("userRole");
	}
}
