package com.woodfish.security.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

/**
 * 用户信息获取逻辑
 * username 认证用户 password 密码   authorities 权限
 * User(String username, String password, Collection<? extends GrantedAuthority> authorities
 */
@Component
public class WoodFishUserDetailService implements UserDetailsService, SocialUserDetailsService {
    private static Logger logger = LoggerFactory.getLogger(WoodFishUserDetailService.class);
    // 加密的规则
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * public interface SocialUserDetails extends UserDetails
     * @param username 传入的用户名
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("表单登录用户名："+username);
        return builderUserDetails(username);
    }

    /**
     *
     * @param userId 此处是一个唯一标识（根据实际情况适配）
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        logger.info("社交登录用户名标识："+userId);
        return builderUserDetails(userId);
    }

    private SocialUser builderUserDetails(String user){

        // 对密码进行加密
        String password = passwordEncoder.encode("123456");
        logger.info("用户的密码是什么要："+ password);
        // 根据查找的用户信息判断用户存在
        return new SocialUser(user,password, true,true,true,true,
                AuthorityUtils.createAuthorityList("admin"));// 集合
    }
}
