package com.patrick.inventorymanagementtask.security.api;

import com.patrick.inventorymanagementtask.security.service.CustomUserDetailsService;
import com.patrick.inventorymanagementtask.utils.ResponseModel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author patrick on 2/8/20
 * @project  inventory
 */
public class TokenAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final Log logger = LogFactory.getLog(this.getClass());
    private final TokenHelper tokenHelper;
    private final CustomUserDetailsService userDetailsService;
    @Value("${jwt.header}")
    private String AUTH_HEADER;
    @Value("${api.access_key}")
    private String API_ACCESS_KEY;

    public TokenAuthenticationFilter(TokenHelper tokenHelper, CustomUserDetailsService userDetailsService) {

        this.tokenHelper = tokenHelper;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String authToken = httpRequest.getHeader("Authorization");
        if (authToken != null && authToken.startsWith("Bearer ")) {
            authToken = authToken.substring(7);
            if (this.tokenHelper.isTokenExpired(authToken)) {
                ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_OK);
                response.setContentType("application/json");
                logger.info("*********** token received as expired "+authToken);
                response.getOutputStream().println(new ResponseModel("tokenExpired","003", "Token expired").toString());
                MDC.clear();
            } else if (!this.tokenHelper.isTokenValid(authToken)) {
                logger.info("===========Invalid Token: " + authToken);
                //((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED);

                ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_OK);
                response.setContentType("application/json");
                response.getOutputStream().println(new ResponseModel("01", "Invalid request").toString());

                MDC.clear();
            } else {
                ((HttpServletResponse) response).setStatus(200);

                String phoneNo = this.tokenHelper.getPhoneNumberFromToken(authToken);

                logger.info("*********TOKEN VALID: " + phoneNo);
                //logger.info("*********TOKEN VALID: "+this.tokenHelper.validateToken(authToken,userDetails.getUsername()));

                if (phoneNo != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(phoneNo);

                    Map<String, Object> ret = this.tokenHelper.validateToken(authToken, userDetails.getUsername());
                    if ((Boolean) ret.get("valid")) {
                        TokenBasedAuthentication authentication = new TokenBasedAuthentication(userDetails);
                        authentication.setToken(authToken);
                        SecurityContextHolder.getContext().setAuthentication(authentication);

                        // Proceed only for successful requests
                        chain.doFilter(request, response);
                        MDC.clear();
                    } else {
                        logger.info("===========Invalid Token Claims: " + authToken);

                        ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_OK);
                        response.setContentType("application/json");
                        response.getOutputStream().println(new ResponseModel("01", "Invalid request").toString());

                        MDC.clear();
                    }
                } else {
                    logger.info("===========Phone not null, Security context: " + phoneNo);

                    ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_OK);
                    response.setContentType("application/json");
                    response.getOutputStream().println(new ResponseModel("01", "Invalid request").toString());

                    MDC.clear();
                }
            }
        } else {

            chain.doFilter(request, response);
            MDC.clear();
        }
    }
}
