package com.project.Managmently.repositories.tenants;

import java.util.List;

import com.project.Managmently.classes.Tenant;

public interface TenantRepository {
    
    void insertTenant(Tenant tenant);

    void deleteTenant(int id);

    List<Tenant> getTenantsForUserByPropertyId(int propertyId);
}
