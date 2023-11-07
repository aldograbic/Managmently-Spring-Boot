package com.project.Managmently.repositories;

import java.util.List;

import com.project.Managmently.classes.Property;

public interface PropertyRepository {

    void insertProperty(Property property);

    List<Property> getPropertiesForUserById(int userId);
}
