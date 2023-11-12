package com.project.Managmently.repositories.properties;

import java.util.List;

import com.project.Managmently.classes.Property;

public interface PropertyRepository {

    void insertProperty(Property property);

    List<Property> getPropertiesForUserById(int userId);

    void deleteProperty(int id);

    int getPropertyIdByName(String name);
    
    void updateProperty(Property property);

    List<Property> searchProperties(String query);
}
