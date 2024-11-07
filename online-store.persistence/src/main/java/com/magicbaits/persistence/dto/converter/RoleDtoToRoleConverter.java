package com.magicbaits.persistence.dto.converter;

import org.springframework.stereotype.Component;

import com.magicbaits.persistence.dto.RoleDto;

@Component
public class RoleDtoToRoleConverter {
	public RoleDto convertRoleNameToRoleDtoWithOnlyRoleName(String roleName) {
		RoleDto roleDto = new RoleDto();
		roleDto.setRoleName(roleName);
		return roleDto;
	}
}
