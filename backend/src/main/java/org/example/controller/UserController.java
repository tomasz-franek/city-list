package org.example.controller;

import org.example.exceptions.SessionException;
import org.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Enumeration;

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private UserService userService;


	@PostMapping(path = "/login")
	public ResponseEntity<?> login() throws SessionException {
		final Enumeration<String> authorizationList = request.getHeaders("Authorization");

		if (!authorizationList.hasMoreElements()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		String authorization = authorizationList.nextElement();

		if (authorizationList.hasMoreElements()) {
			throw new SessionException("Problem with token in request");
		}
		return authorize(authorization);
	}

	private ResponseEntity<?> authorize(String authorization) {
		if (authorization != null && authorization.startsWith("Basic")) {
			String base64Credentials = authorization.substring("Basic".length()).trim();
			String credentials = new String(Base64.getDecoder().decode(base64Credentials.getBytes()), StandardCharsets.UTF_8);
			final String[] values = credentials.split(":", 2);
			if (values.length == 2) {
				return userService.createSession(values);
			} else {
				return new ResponseEntity<>("Wrong credentials", HttpStatus.BAD_REQUEST);
			}
		} else {
			LOGGER.warn("authorization problem ");
			return new ResponseEntity<>("problem with authorization",HttpStatus.BAD_REQUEST);
		}
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
