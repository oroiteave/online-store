package com.magicbaits.persistence.dto.converter;

import com.magicbaits.persistence.dto.RoleDto;

public class RoleDtoToRoleConverter {
	public RoleDto convertRoleNameToRoleDtoWithOnlyRoleName(String roleName) {
		RoleDto roleDto = new RoleDto();
		roleDto.setRoleName(roleName);
		return roleDto;
	}
}
