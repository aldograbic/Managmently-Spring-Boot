package com.project.Managmently.repositories;

import java.util.List;

import com.project.Managmently.classes.Contact;
import com.project.Managmently.classes.User;

public interface UserRepository {
    
    User findByUsername(String username);

    User findByEmail(String email);

    void saveUser(User user);

    void contact(String name, String email, String message);

    void insertContact(Contact contact);

    List<Contact> getContactsForUserById(int userId);
}
