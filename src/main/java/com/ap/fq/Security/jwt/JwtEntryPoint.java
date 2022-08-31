package com.ap.fq.Security.jwt;

import org.slf4j.LoggerFactory;
import javax.servlet.ServletException;
import java.io.IOException;
import org.springframework.security.core.AuthenticationException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.security.web.AuthenticationEntryPoint;

@Component
public class JwtEntryPoint implements AuthenticationEntryPoint
{
    private static final Logger logger;
    
    @Override
    public void commence(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException authException) throws IOException, ServletException {
        JwtEntryPoint.logger.error("Fall\u00f3 el metodo commence ");
        response.sendError(401);
    }
    
    static {
        logger = LoggerFactory.getLogger((Class)JwtEntryPoint.class);
    }
}