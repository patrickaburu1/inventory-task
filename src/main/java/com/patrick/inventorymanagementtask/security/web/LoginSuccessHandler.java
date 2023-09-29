package com.patrick.inventorymanagementtask.security.web;


import com.patrick.inventorymanagementtask.entities.products.Shop;
import com.patrick.inventorymanagementtask.entities.user.Users;
import com.patrick.inventorymanagementtask.repositories.product.ShopRepository;
import com.patrick.inventorymanagementtask.repositories.user.UserRepository;
import com.patrick.inventorymanagementtask.security.SecurityUtils;
import com.patrick.inventorymanagementtask.security.service.CustomUserDetailsService;
import com.patrick.inventorymanagementtask.service.DashboardService;
import com.patrick.inventorymanagementtask.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author patrick on 11/21/19
 * @project  inventory
 */
@Component
@Transactional
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private DashboardService dashboardService;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private ShopService shopService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication auth) throws IOException, ServletException {

        /*Set various session parameters*/
        HttpSession session = request.getSession();
        String username = SecurityUtils.getCurrentUserLogin();

        // Get the user who has logged into the app
        Users user = userDetailsService.getAuthicatedUser();
        session.setAttribute("_userNo", user.getId());
        session.setAttribute("_user", user);
        //   session.setAttribute("_selectedShop", false);

        Shop defaultShop = shopService.getUserDefaultShop(user);
        if (null != defaultShop) {
            dashboardService.selectShop(request, defaultShop.getId());
        }

        logger.info("******* session set  on login ");

        handle(request, response, auth);
        clearAuthenticationAttributes(request);

      /*  int timeout = 30;
        session.setMaxInactiveInterval(timeout * 60);*/

        dashboardService.saveAuditLogWeb("Login", user, request);

        // Call the superclass method
        super.onAuthenticationSuccess(request, response, auth);
    }
}
