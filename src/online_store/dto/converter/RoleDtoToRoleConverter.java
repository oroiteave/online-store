package online_store.dto.converter;

import online_store.dto.RoleDto;

public class RoleDtoToRoleConverter {
	public RoleDto convertRoleNameToRoleDtoWithOnlyRoleName(String roleName) {
		RoleDto roleDto = new RoleDto();
		roleDto.setRoleName(roleName);
		return roleDto;
	}
}
