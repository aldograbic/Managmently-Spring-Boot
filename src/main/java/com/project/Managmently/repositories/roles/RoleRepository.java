package com.project.Managmently.repositories.roles;

import com.project.Managmently.classes.Role;

public interface RoleRepository {
    Role findById(int roleId);
}
