package com.project.Managmently.repositories;

import com.project.Managmently.classes.User;

public interface UserRepository {
    
    User findByUsername(String username);

    User findByEmail(String email);

    void saveUser(User user);

    void contact(String name, String email, String message);
}
