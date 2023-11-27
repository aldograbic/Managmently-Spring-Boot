package com.project.Managmently.repositories.contacts;

import java.util.List;

import com.project.Managmently.classes.User;

public interface ContactRepository {
    
    void addNewContact(int userId, int contactId);

    void deleteContact(int id);

    List<User> getContactsForUserById(int userId);

    List<User> searchContacts(String query);

    void sendFriendRequest(int userId, int contactUserId);
}
