package org.example.controller;

import org.example.TestCities;
import org.example.domain.City;
import org.example.exceptions.SessionException;
import org.example.repository.CityRepository;
import org.example.repository.RoleRepository;
import org.example.repository.UserRepository;
import org.example.service.CityService;
import org.example.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(CityController.class)
@ExtendWith(MockitoExtension.class)
public class CityControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CityController cityController;

	@MockBean
	private CityService cityService;

	@MockBean
	private UserService userService;

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private CityRepository cityRepository;

	@MockBean
	private RoleRepository roleRepository;


	@Test
	public void listCity_noParameters_correctResponse() throws Exception {
		testEndpointCityList("/city/list");
	}

	@Test
	public void endpointCityList_parameterPage_correctResponse() throws Exception {
		testEndpointCityList("/city/list?page=3");
	}

	@Test
	public void endpointCityList_parameterSize_correctResponse() throws Exception {
		testEndpointCityList("/city/list?size=3");
	}

	@Test
	public void endpointCityList_parameterName_correctResponse() throws Exception {

		testEndpointCityList("/city/list?name=x");
	}

	@Test
	public void endpointCityList_objectNotInitialized_returnBadRequest() throws Exception {
		doCallRealMethod().when(cityController).listCities(any(Pageable.class),any(String.class));
		mockMvc.perform(get("/city/list"))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void endpointCityList_wrongToken_returnUnauthorized() throws Exception {
		doThrow(new SessionException("test")).when(userService).wrapRequest(any());
		doCallRealMethod().when(cityController).listCities(any(Pageable.class),any(String.class));
		doReturn(userService).when(cityController).getUserService();
		mockMvc.perform(get("/city/list"))
				.andExpect(status().isUnauthorized());
	}

	private void testEndpointCityList(String url) throws Exception {
		Page<City> cityPage = new PageImpl<>(TestCities.prepareTestCities());
		ResponseEntity<Page<City>> responseEntity = new ResponseEntity<>(cityPage, HttpStatus.OK);
		doReturn(responseEntity).when(cityController).listCities(any(), any());

		mockMvc.perform(get(url))
				.andExpect(status().isOk())
				.andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.size", is(2)));
	}
}
