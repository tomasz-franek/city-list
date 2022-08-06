package org.example.repository;

import org.example.domain.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends PagingAndSortingRepository<City,Long> {
	Page<City> findAll(Pageable pageable);
	Page<City> findAllByNameContaining(Pageable pageable, String name);
}
