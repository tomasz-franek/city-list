package org.example.service;

import org.example.domain.Role;

import java.util.List;

public interface IRoleService {
	List<Role> findRolesByUserId(Long userId);
}
