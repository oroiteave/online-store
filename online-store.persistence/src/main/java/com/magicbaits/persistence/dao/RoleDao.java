package com.magicbaits.persistence.dao;

import com.magicbaits.persistence.dto.RoleDto;

public interface RoleDao {
	RoleDto getRoleById(int id);
	RoleDto getRoleByName(String roleName);
}
