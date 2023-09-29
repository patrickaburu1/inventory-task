package com.patrick.inventorymanagementtask.security.api;


import com.patrick.inventorymanagementtask.entities.user.UserTypes;
import com.patrick.inventorymanagementtask.entities.user.Users;
import com.patrick.inventorymanagementtask.repositories.user.UserRepository;
import com.patrick.inventorymanagementtask.repositories.user.UserTypeRepository;
import com.patrick.inventorymanagementtask.utils.AppFunctions;
import groovyjarjarpicocli.CommandLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.InvalidAlgorithmParameterException;
import java.util.Date;

/**
 * @author patrick on 2/8/20
 * @project shop-pos
 */
@Component
public class ShopAuthenticationProvider extends DaoAuthenticationProvider {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AppFunctions appFunctions;
    @Autowired
    private UserTypeRepository userTypeRepository;


    @Autowired
    @Qualifier("customUserDetailsService")
    @Override
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        super.setUserDetailsService(userDetailsService);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        super.setPasswordEncoder(passwordEncoder);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        StringBuilder error = new StringBuilder();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = request.getHeader("X-Forwarded-For") == null ? request.getRemoteAddr() : request.getHeader("X-Forwarded-For");

        String phoneNumber = authentication.getName();
        if (!appFunctions.validatePhoneNumber(phoneNumber)) {
            error.append("Phone number supplied is invalid!");
            throw new BadCredentialsException(error.toString());
        }
        phoneNumber = appFunctions.getInternationalPhoneNumber(phoneNumber, "");
       /* authentication = new UsernamePasswordAuthenticationToken(phoneNumber, authentication.getCredentials());
        authentication = new UsernamePasswordAuthenticationToken(phoneNumber, authentication.getCredentials());*/

        UserTypes userType=userTypeRepository.findFirstByName(UserTypes.PORTAL_USER_TYPE);
        Users user=userRepository.findByPhoneAndUserType(phoneNumber,userType.getId());

        if (null==user){
            error.append("Sorry invalid credentials!");
            throw new BadCredentialsException(error.toString());
        }

        try {
            Authentication auth = super.authenticate(authentication);
            if (userRepository.findByPhone(phoneNumber) != null) {
                String phone = userRepository.findByPhone(phoneNumber).getPhone();
            }
            user.setLastLogin(new Date());
            user.setIp(ip);
            userRepository.save(user);

            return auth;
        } catch (LockedException e) {
            error.append("User account is locked");
            throw new LockedException(error.toString());
        } catch (UsernameNotFoundException e) {
            error.append("User does not exist");
            throw new UsernameNotFoundException(error.toString());
        } catch (CredentialsExpiredException e) {
            throw new CredentialsExpiredException(error.toString());
        } catch (BadCredentialsException e) {
            if (userRepository.findByPhone(phoneNumber) != null) {
                String phone = userRepository.findByPhone(phoneNumber).getPhone();

               /* //Invalid login: update user attempts
                long remainingRetries = this.userAttemptsService.updateFailedAttempts(email);

                if (remainingRetries > 0) {
                    error.append("Invalid email/password");
                    if (remainingRetries == 1) {
                        error.append(": 1 attempt remaining");
                    } else {
                        error.append(": ").append(remainingRetries).append(" attempts remaining");
                    }
                } else {
                    error.append(this.userAttemptsService.handleLockedAccount(email));
                }
                AppAuditLog log = new AppAuditLog()
                        .setLogType(AppAuditLog.USER_GENERATED)
                        .setActivity("Attempt to log into the system:" + error.toString())
                        .setNewValue("Log out")
                        .setOldValue("Log out")
                        .setStatus("Failed");
                logService.saveLog(ip, log, email);*/
                error.append("Sorry credentials does't match our records.");
                throw new BadCredentialsException(error.toString());
            } else {
                error.append("User does not exist");
                throw new UsernameNotFoundException(error.toString());
            }
        }
    }
}
