package com.sivalabs.springjsfjpa.services;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Service;

;

@Service
public class SpringRealm extends AuthorizingRealm {

    public SpringRealm() {
	// TODO Auto-generated constructor stub
    }

    public SpringRealm(CacheManager cacheManager) {
	super(cacheManager);
	// TODO Auto-generated constructor stub
    }

    public SpringRealm(CredentialsMatcher matcher) {
	super(matcher);
	// TODO Auto-generated constructor stub
    }

    public SpringRealm(CacheManager cacheManager, CredentialsMatcher matcher) {
	super(cacheManager, matcher);
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
	SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
	// info.setRoles(roles); //fill in roles
	// info.setObjectPermissions(permissions); //add permisions (MUST
	// IMPLEMENT SHIRO PERMISSION INTERFACE)

	return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
	    AuthenticationToken arg0) throws AuthenticationException {
	return new SimpleAuthenticationInfo("user", "password", getName());
    }

}
