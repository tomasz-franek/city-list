package org.example.service;

import org.example.domain.City;
import org.example.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
public class CityService implements ICityService {

	@Autowired
	private CityRepository cityRepository;

	@Override
	public Long countCities() {
		return getCityRepository().count();
	}

	@Override
	public Page<City> getCities(Pageable pageable) {
		return getCityRepository().findAll(pageable);
	}

	@Override
	@Transactional
	public City updateCity(City newCityData) {
		City city = getCityRepository().findById(newCityData.getCityId()).orElse(null);
		if(city != null) {
			city.setName(newCityData.getName());
			city.setPhotoLink(newCityData.getPhotoLink());
			return getCityRepository().save(city);
		}
		return null;
	}

	@Override
	public Page<City> getCitiesByName(Pageable pageable, String name) {
		return  getCityRepository().findAllByNameContaining(pageable,name);
	}

	public CityRepository getCityRepository() {
		return cityRepository;
	}

	public void setCityRepository(CityRepository cityRepository) {
		this.cityRepository = cityRepository;
	}
}
