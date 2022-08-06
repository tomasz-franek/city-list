package org.example.service;


import org.example.domain.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICityService {
	Long countCities();
	Page<City> getCities(Pageable pageable);
	City updateCity(City city);
	Page<City> getCitiesByName(Pageable pageable, String name);
}
