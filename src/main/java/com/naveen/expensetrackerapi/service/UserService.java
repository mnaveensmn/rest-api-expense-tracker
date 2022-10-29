package com.naveen.expensetrackerapi.service;

import com.naveen.expensetrackerapi.entity.User;
import com.naveen.expensetrackerapi.entity.UserModel;

import java.util.Optional;

public interface UserService {

    User createUser(UserModel user);

    User findUser();

    User updateUser(User user);

    void deleteUser();

    User getLoggedInUser();
}
