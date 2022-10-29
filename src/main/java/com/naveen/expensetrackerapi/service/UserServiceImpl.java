package com.naveen.expensetrackerapi.service;

import com.naveen.expensetrackerapi.entity.User;
import com.naveen.expensetrackerapi.entity.UserModel;
import com.naveen.expensetrackerapi.exceptions.ItemAlreadyExistsException;
import com.naveen.expensetrackerapi.exceptions.ResourceNotFoundException;
import com.naveen.expensetrackerapi.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public User createUser(UserModel uModel) {
        if (userRepository.existsByEmail(uModel.getEmail())) {
            throw new ItemAlreadyExistsException("User is already registered with email: " + uModel.getEmail());
        }
        User user = new User();
        BeanUtils.copyProperties(uModel, user);
        user.setPassword(bcryptEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User findUser() {
        return userRepository
                .findById(getLoggedInUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
    }

    @Override
    public User updateUser(User user) {
        User existingUser = findUser();
        existingUser.setName(user.getName() != null ? user.getName() : existingUser.getName());
        existingUser.setEmail(user.getEmail() != null ? user.getEmail() : existingUser.getEmail());
        existingUser.setPassword(user.getPassword() != null ? bcryptEncoder.encode(user.getPassword()) : existingUser.getPassword());
        existingUser.setAge(user.getAge() != null ? user.getAge() : existingUser.getAge());
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser() {
        User user = findUser();
        userRepository.delete(user);
    }

    @Override
    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("User not found for the email" + email));
    }
}
