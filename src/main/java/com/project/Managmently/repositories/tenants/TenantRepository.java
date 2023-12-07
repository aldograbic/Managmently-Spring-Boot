package com.project.Managmently.repositories.tenants;

import java.util.List;

import com.project.Managmently.classes.Tenant;
import com.project.Managmently.classes.User;

public interface TenantRepository {
    
    void insertTenant(Tenant tenant, int userId);

    void deleteTenant(int id);

    List<Tenant> getTenantsForUserByPropertyId(int propertyId);

    Tenant getTenantById(int id);

    void updateTenant(Tenant tenant);

    List<Tenant> searchTenants(String query);

    // List<Tenant> getAllTenants();

    int getTenantCountForUserById(int userId);

    String getPropertyNameByTenantId(int tenantId);

    User getPropertyOwnerByTenantId(int tenantId);
}
