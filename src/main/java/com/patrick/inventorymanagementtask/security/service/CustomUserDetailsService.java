package com.patrick.inventorymanagementtask.security.service;

import com.patrick.inventorymanagementtask.entities.user.Users;
import com.patrick.inventorymanagementtask.repositories.user.UserRepository;
import com.patrick.inventorymanagementtask.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @author patrick on 2/8/20
 * @project  inventory
 */
@Transactional
@Service("customUserDetailsService")
public class CustomUserDetailsService  implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(final String phoneNumber) throws UsernameNotFoundException {

        Users user = userRepository.findByPhone(phoneNumber);

        if (null == user) {
            throw new UsernameNotFoundException("No such user exists");
        }

        return buildAuthDetails(user);
    }

    private UserDetails buildAuthDetails(Users user) {
        // Iterate the list
        List<GrantedAuthority> result = new ArrayList<>();
        List<String> userRoles = new ArrayList<>();
        String field;
       /* boolean parentStatus;
        boolean enabled = (!user.getActive() && user.getUsergroup().getFlag().equals("ok") && user.getFlag().equals(AbstractRepository.ACTIVE_RECORD));
        boolean passwordNonExpired = user.getPasswordExpiry().after(new Date());
        boolean isAccountNonLocked = this.userAttemptsRepository.isAccountNonLocked(user.getEmail());
        Set<Permissions> groupPermissions = user.getUsergroup().getPermissions();
        for (Permissions row : groupPermissions) {
            field = row.getRole().getRoleCode();
            if (!userRoles.contains(field)) {
                result.add(new SimpleGrantedAuthority(field));
                userRoles.add(field);
            }
            if (!row.getActionCode().equals("default")) {
                GrantedAuthority action = new SimpleGrantedAuthority(field + "_" + row.getActionCode().toUpperCase());
                if (!result.contains(action)) {
                    result.add(action);
                }
            }
        }
        if (user.getEmail().contains("riverbank")) {
            result.add(new SimpleGrantedAuthority("ROLE_DOCUMENTATION"));
            result.add(new SimpleGrantedAuthority("ROLE_RB"));
        }
        String userType = user.getUsergroup().getUsertype().getName();
        switch (userType) {
            case BANK_ADMIN_TYPE:
                Bank bank = bankRepository.findById(user.getUsergroup().getBankId()).get();
                parentStatus = (bank.getStatus()) == 1;
                break;
            case CTP_ADMIN_TYPE:
                CTP ctp = ctpRepository.findById(user.getUsergroup().getCtpId()).get();
                parentStatus = (ctp.getStatus()) == 1;
                break;
            default:
                parentStatus = true;
                break;
        }*/
        return new User(user.getPhone(), user.getPassword(), true,
                true, true, true, result);
    }

    public Users getAuthicatedUser(){
     return userRepository.findByPhone(SecurityUtils.getCurrentUserLogin());

    }
}
