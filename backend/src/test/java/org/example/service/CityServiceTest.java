package org.example.service;

import org.example.TestCities;
import org.example.domain.City;
import org.example.repository.CityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class CityServiceTest {

	@MockBean
	private CityService service;

	@MockBean
	private CityRepository cityRepository;

	private Page<City> cityPage;

	@Test
	public void getCities_pageableOnlyParameter_returnResult() {
		Pageable pageable = Pageable.ofSize(2).withPage(0);

		cityPage = new PageImpl<>(TestCities.prepareTestCities());
		service = Mockito.mock(CityService.class);
		when(service.getCities(pageable)).thenReturn(cityPage);
		Page<City> response = service.getCities(pageable);
		assertThat(response,is(notNullValue()));
		assertThat(response.getSize(),is(2));

		verify(service, times(1)).getCities(pageable);
	}

	@Test
	public void getCitiesByName_pageableAndCityName_returnResult() {
		Pageable pageable = Pageable.ofSize(2).withPage(0);
		String cityName = "n";

		service = Mockito.mock(CityService.class);
		cityPage = new PageImpl<>(TestCities.prepareTestCities());
		when(service.getCitiesByName(pageable,cityName)).thenReturn(cityPage);

		Page<City> response = service.getCitiesByName(pageable,cityName);

		assertThat(response,is(notNullValue()));
		assertThat(response.getSize(),is(2));
		assertThat(response.getContent().get(0).getName(),is("Berlin"));
		assertThat(response.getContent().get(0).getPhotoLink(),is("BerlinLink"));
		assertThat(response.getContent().get(1).getName(),is("London"));
		assertThat(response.getContent().get(1).getPhotoLink(),is("LondonLink"));


		verify(service, times(1)).getCitiesByName(pageable,cityName);
	}

	@Test
	public void updateCity_correctCityId_dataUpdated(){

		City oldCityData = new City();
		oldCityData.setPhotoLink("oldlink");
		oldCityData.setCityId(1L);
		oldCityData.setName("oldname");

		City newCityData = new City();
		newCityData.setPhotoLink("link");
		newCityData.setCityId(1L);
		newCityData.setName("name");

		doReturn(Optional.of(oldCityData)).when(cityRepository).findById(newCityData.getCityId());
		doReturn(newCityData).when(cityRepository).save(oldCityData);
		doReturn(cityRepository).when(service).getCityRepository();
		doCallRealMethod().when(service).updateCity(any());

		City response = service.updateCity(newCityData);
		assertThat(response,is(notNullValue()));
		assertThat(response.getCityId(),is(newCityData.getCityId()));
		assertThat(response.getPhotoLink(),is(newCityData.getPhotoLink()));
		assertThat(response.getName(),is(newCityData.getName()));
	}

	@Test
	public void updateCity_incorrectCityId_nullValueReturned(){
		City newCityData = new City();
		newCityData.setPhotoLink("link");
		newCityData.setCityId(1L);
		newCityData.setName("name");

		doReturn(Optional.empty()).when(cityRepository).findById(any());
		City response = service.updateCity(newCityData);
		assertThat(response,is(nullValue()));
	}
}
