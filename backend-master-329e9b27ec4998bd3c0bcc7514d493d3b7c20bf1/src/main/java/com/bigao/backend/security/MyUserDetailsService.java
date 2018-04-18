package com.bigao.backend.security;

import com.bigao.backend.mapper.UserMapper;
import com.bigao.backend.module.sys.auth.AuthenticationResult;
import com.bigao.backend.module.sys.model.User;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * @author yuan-hai
 *         主要实现登陆认证
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    private Cache<String, List<String>> userAuth = CacheBuilder.newBuilder().maximumSize(30).build();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Collection<GrantedAuthority> auth = Lists.newArrayList();
        User user = userMapper.getUserByName(username);
        List<String> authStr = userMapper.loadUserAuthoritiesByName(username);
        for (String authName : authStr) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(authName);
            auth.add(authority);
        }
        user.setAuthorities(auth);
        return user;
    }

    public UserDetails loadInfo(AuthenticationResult result) {
        Collection<GrantedAuthority> auth = Lists.newArrayList();
        User user = new User();
        try {
            for (String authName : result.getPermissionList()) {
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(authName);
                auth.add(authority);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        user.setAuthorities(auth);
        return user;
    }

}
