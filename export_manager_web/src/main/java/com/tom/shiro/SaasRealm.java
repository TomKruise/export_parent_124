package com.tom.shiro;

import com.tom.dao.system.UserDao;
import com.tom.domain.system.Module;
import com.tom.domain.system.User;
import com.tom.service.system.ModuleService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SaasRealm extends AuthorizingRealm {
    @Autowired
    private UserDao userDao;

    @Autowired
    private ModuleService moduleService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        User user = (User) principalCollection.getPrimaryPrincipal();

        List<Module> moduleList = moduleService.findModulesByUser(user);

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        for (Module module : moduleList) {
            authorizationInfo.addStringPermission(module.getName());
        }

        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        String email = token.getUsername();

        User user = userDao.findByEmail(email);
        if (null == user)
            return null;
        return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
    }
}
