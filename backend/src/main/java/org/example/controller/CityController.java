package org.example.controller;

import org.example.domain.City;
import org.example.domain.Role;
import org.example.domain.RolesEnum;
import org.example.domain.User;
import org.example.exceptions.SessionException;
import org.example.service.CityService;
import org.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/city", produces = MediaType.APPLICATION_JSON_VALUE)
public class CityController {
	private static final Logger LOGGER = LoggerFactory.getLogger(CityController.class);

	@Autowired
	private CityService cityService;

	@Autowired
	private UserService userService;

	@Autowired
	private HttpServletRequest httpServletRequest;


	@GetMapping(path = "/list")
	public ResponseEntity<?> listCities(
			@PageableDefault(size = 20)
			@SortDefault.SortDefaults({
					@SortDefault(sort = "name", direction = Sort.Direction.ASC, caseSensitive = false)
			}) Pageable pageable, @RequestParam(value = "name", defaultValue = "") String name) {
		try {
			getUserService().wrapRequest(httpServletRequest);
			if(StringUtils.isEmpty(name)) {
				return new ResponseEntity<>(cityService.getCities(pageable), HttpStatus.OK);
			}else{
				return new ResponseEntity<>(cityService.getCitiesByName(pageable,name), HttpStatus.OK);
			}
		} catch (SessionException ex) {
			return new ResponseEntity<>("Unauthorized request", HttpStatus.UNAUTHORIZED);
		} catch (Exception ex) {
			LOGGER.info("list cities", ex);
			return new ResponseEntity<>("Problem with list cities", HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping(path = "/update")
	public ResponseEntity<?> updateCity(@RequestBody City city) {
		try {
			User user = getUserService().wrapRequest(httpServletRequest);
			List<String > roles= user.getRoles().stream().map(Role::getName).collect(Collectors.toList());
			if (roles.stream().anyMatch(name -> RolesEnum.EDITOR.toString().equals(name))) {
				return new ResponseEntity<>(cityService.updateCity(city), HttpStatus.OK);
			}
		} catch (SessionException ex) {
			return new ResponseEntity<>("Unauthorized request", HttpStatus.UNAUTHORIZED);
		} catch (Exception ex) {
			LOGGER.error("update city", ex);
			return new ResponseEntity<>("Problem with update city data", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
	}

	public CityService getCityService() {
		return cityService;
	}

	public void setCityService(CityService cityService) {
		this.cityService = cityService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
