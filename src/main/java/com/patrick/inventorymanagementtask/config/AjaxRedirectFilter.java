package com.patrick.inventorymanagementtask.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author patrick on 7/8/20
 * @project inventory
 */
public class AjaxRedirectFilter extends GenericFilterBean {
    private static final Logger logger = LoggerFactory.getLogger(AjaxRedirectFilter.class);

    /**
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     * @apiNote Filter bean implimatation to allow redirect to login for ajax requests
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String ajaxHeader = ((HttpServletRequest) request).getHeader("X-Requested-With");
        boolean ajaxRequestReceived = "XMLHttpRequest".equals(ajaxHeader);
        boolean sessionExpired;

        HttpServletRequest httpRequest;
        httpRequest = (HttpServletRequest) request;
        sessionExpired = hasSessionExpired(httpRequest);
        if (sessionExpired && ajaxRequestReceived) {
            performRedirect(httpRequest, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    private boolean hasSessionExpired(HttpServletRequest httpRequest) {
        if (httpRequest.getRequestedSessionId() != null
                && !httpRequest.isRequestedSessionIdValid()) {
            if (logger.isDebugEnabled()) {
                logger.debug("Requested session ID "
                        + httpRequest.getRequestedSessionId() + " is invalid.");
            }
            return true;
        }
        return false;
    }

    private void performRedirect(HttpServletRequest request,
                                 ServletResponse response) {
        HttpServletResponse resp = (HttpServletResponse) response;
        String contextPath = request.getContextPath();
        String redirectUrl = contextPath + "/login?expired";
        logger.info(
                "Session expired during ajax request, redirecting to '{}'",
                redirectUrl);

        String ajaxRedirectXml = createAjaxRedirectXml(redirectUrl);
        logger.debug("Ajax partial response to redirect: {}", ajaxRedirectXml);
        resp.setStatus(401);
    }

    private String createAjaxRedirectXml(String redirectUrl) {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<partial-response><redirect url=\"" +
                redirectUrl +
                "\"></redirect></partial-response>";
    }
}
