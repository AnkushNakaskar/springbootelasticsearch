package com.elasticsearch.demo.controller;

import com.elasticsearch.demo.UserService;
import com.elasticsearch.demo.vo.User;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author ankushnakaskar
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @RequestMapping("/id/{userId}")
    public User getUser(@PathVariable String userId) {
        User user = userService.getUserById(userId);
        return user;
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public User addNewUsers(@RequestBody User user) {
        userService.addNewUser(user);
        return user;
    }

    @RequestMapping(value = "/settings/{name}", method = RequestMethod.GET)
    public Object getAllUserSettings(@PathVariable String name) {
        return userService.getAllUserSettings(name);
    }

    @RequestMapping(value = "/settings/{name}/{key}", method = RequestMethod.GET)
    public String getUserSetting(
            @PathVariable String name, @PathVariable String key) {
        return userService.getUserSetting(name, key);
    }

    @RequestMapping(value = "/settings/{name}/{key}/{value}", method = RequestMethod.GET)
    public String addUserSetting(
            @PathVariable String name,
            @PathVariable String key,
            @PathVariable String value) {
        return userService.addUserSetting(name, key, value);
    }
}
