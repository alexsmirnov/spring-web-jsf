package com.sivalabs.springjsfjpa.web;

import static org.springframework.web.context.WebApplicationContext.SCOPE_REQUEST;

import java.io.IOException;

import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.context.annotation.Scope;

@Named
@Scope(SCOPE_REQUEST)
public class Login {

    public static final String HOME_URL = "/index.xhtml";

    private String username;
    private String password;
    private boolean remember;

    public void submit() throws IOException {
            FacesContext facesContext = FacesContext.getCurrentInstance();
        try {
            ExternalContext externalContext = facesContext.getExternalContext();
            SecurityUtils.getSubject().login(new UsernamePasswordToken(username, password, remember));
            SavedRequest savedRequest = WebUtils.getAndClearSavedRequest((ServletRequest) externalContext.getRequest());
            facesContext.responseComplete();
            externalContext.redirect(savedRequest != null ? savedRequest.getRequestUrl() : HOME_URL);
        }
        catch (AuthenticationException e) {
            throw new FacesException(e);
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRemember() {
        return remember;
    }

    public void setRemember(boolean remember) {
        this.remember = remember;
    }

    // Add/generate getters+setters.
    
}
