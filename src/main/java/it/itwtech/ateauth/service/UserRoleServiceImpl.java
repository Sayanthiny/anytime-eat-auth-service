package it.itwtech.ateauth.service;
//package com.fleet.management.oauth.server.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.fleet.management.oauth.server.entities.Role;
//import com.fleet.management.oauth.server.entities.User;
//import com.fleet.management.oauth.server.entities.UserRole;
//import com.fleet.management.oauth.server.enums.UserType;
//import com.fleet.management.oauth.server.repositories.UserRoleRepository;
//
//@Service
//public class UserRoleServiceImpl implements UserRoleService {
//	@Autowired
//	private UserRoleRepository userRoleRepository;
//
//	@Transactional(readOnly = true)
//	public UserRole getRoleByUserId(Long id) {
//		return userRoleRepository.findByUserId(id);
//	}
//
//	@Transactional
//	public void addRoleToUser(User user) {
//		UserRole userRole = new UserRole();
//		userRole.setUser(user);
//
//		Role role = new Role();
//		if (UserType.STANDALONEUSER == user.getUserType()) {
//			role.setId(1L);
//			userRole.setRole(role);
//		}
//		userRoleRepository.save(userRole);
//	}
//
//}
