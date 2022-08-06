package org.example;

import org.assertj.core.util.Lists;
import org.example.domain.City;

import java.util.List;

public class TestCities {
	public static List<City> prepareTestCities() {
		City berlin = new City();
		berlin.setCityId(1L);
		berlin.setName("Berlin");
		berlin.setPhotoLink("BerlinLink");
		City london = new City();
		london.setCityId(1L);
		london.setName("London");
		london.setPhotoLink("LondonLink");
		return Lists.newArrayList(berlin,london);
	}
}
