/*
 * Copyright (c) 2020. All rights reserved.
 * Author: Xin Li
 * Email: helloimlixin@gmail.com
 */

package secondHandMarket.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import secondHandMarket.model.User;
import secondHandMarket.repository.UserRepository;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(String id) {
        Iterable<User> users = userRepository.findAll();
        for (User user: users) {
            if (user.getUserUID().equals(id)) {
                return user;
            }
        }

        return null;
    }

    @Override
    public User save(User user) {
        Iterable<User> existingUsers = userRepository.findAll();
        for (User existingUser: existingUsers) {
            if (existingUser.getUserUID().equals(user.getUserUID())) {
                System.out.println("DEBUG: User already exists.");
                return null;
            }
        }

        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }
}
