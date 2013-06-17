/**
 * 
 */
package com.sivalabs.springjsfjpa.web;

import java.util.List;

import javax.inject.Inject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sivalabs.springjsfjpa.entities.User;
import com.sivalabs.springjsfjpa.services.UserService;

/**
 * @author Siva
 * 
 */
@Scope("request")
@Component
public class UserController {

    private UserService userService;

    private List<User> users = null;

    @Inject
    public UserController(UserService userService) {
	this.userService = userService;
    }

    public void setUsers(List<User> users) {
	this.users = users;
    }

    public List<User> getUsers() {
	if (users == null) {
	    users = userService.findAllUsers();
	}
	return users;
    }
}
