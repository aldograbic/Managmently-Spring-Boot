package com.project.Managmently.repositories.user;

import java.util.List;

import com.project.Managmently.classes.User;

public interface UserRepository {
    
    User findByUsername(String username);

    User findByEmail(String email);

    void saveUser(User user);

    void contact(String name, String email, String message);

    void updateUser(User user);

    void deleteUser(User user);

    User findByConfirmationToken(String token);

    void updateEmailVerification(User user);

    List<User> searchUsers(String query);
}
