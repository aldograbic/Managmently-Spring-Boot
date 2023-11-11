package com.project.Managmently.repositories.contacts;

import java.util.List;

import com.project.Managmently.classes.Contact;

public interface ContactRepository {
    
    void insertContact(Contact contact);

    void deleteContact(int id);

    List<Contact> getContactsForUserById(int userId);

    void updateContact(Contact contact);
}
