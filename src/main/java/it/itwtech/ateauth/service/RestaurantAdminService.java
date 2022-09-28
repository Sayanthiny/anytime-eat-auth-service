package it.itwtech.ateauth.service;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import it.itwtech.ateauth.dto.RestaurantAdminUserDto;
import it.itwtech.ateauth.model.RestaurantAdmin;

public interface RestaurantAdminService {

	public String saveRestaurantAdmin(@Valid RestaurantAdminUserDto userDto, HttpServletRequest request);

	public RestaurantAdmin findRestaurantAdminById(Long id);
}
