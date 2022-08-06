package org.example.controller;

import org.example.domain.Role;
import org.example.domain.User;
import org.example.exceptions.SessionException;
import org.example.repository.CityRepository;
import org.example.repository.RoleRepository;
import org.example.repository.UserRepository;
import org.example.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
	@Autowired
	private MockMvc mockMvc;


	@MockBean
	private RoleRepository roleRepository;

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private CityRepository cityRepository;

	@MockBean
	private UserService userService;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Test
	void login_incorrectData_badRequestStatus() throws Exception {

		mockMvc.perform(post("/user/login").header("Authorization","x"))
				.andExpect(status().isBadRequest());
	}

	@Test
	void login_invalidCredentials_badRequestStatus() throws Exception {
		doThrow(new SessionException("")).when(userService).wrapRequest(any(HttpServletRequest.class));
		mockMvc.perform(post("/user/login").header("Authorization","Basic YT"))
				.andExpect(status().isBadRequest());
	}

	@Test
	void login_correctCredentials_returnedJwtToken() throws Exception {
		User user=new User();
		user.setUserId(1L);
		user.setPassword("pass");
		user.setName("nam");
		Role roleUser = new Role();
		roleUser.setRoleId(1L);
		roleUser.setName("USER");
		roleUser.setUserId(user.getUserId());
		user.getRoles().add(roleUser);

		doReturn(user).when(userService).wrapRequest(any(HttpServletRequest.class));
		doCallRealMethod().when(userService).createSession(any());
		doReturn(Optional.of(user)).when(userRepository).findByNameAndPassword(any(),any());
		doReturn(userRepository).when(userService).getUserRepository();
		mockMvc.perform(post("/user/login").header("Authorization","Basic YTph"))
				.andExpect(status().isAccepted())
				.andExpect(header().exists("TOKEN"))
				.andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
				.andExpect(content().string("{\"userId\":1,\"name\":\"nam\",\"password\":\"pass\",\"roles\":[{\"roleId\":1,\"userId\":1,\"name\":\"USER\"}]}"));
	}
}
