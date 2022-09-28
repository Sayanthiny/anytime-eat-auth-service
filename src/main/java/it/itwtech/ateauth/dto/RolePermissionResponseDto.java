package it.itwtech.ateauth.dto;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RolePermissionResponseDto {
	private Long roleId;
	private Set<String> permissionNames;
}
