package com.scalples;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.scalples.mapper.HotelMapper;
import com.scalples.repository.CityDao;

@SpringBootApplication
public class FountainApplication {

	public static void main(String[] args) {
		SpringApplication.run(FountainApplication.class, args);
	}
	
	private final CityDao cityDao;

	private final HotelMapper hotelMapper;

	public FountainApplication(CityDao cityDao, HotelMapper hotelMapper) {
		this.cityDao = cityDao;
		this.hotelMapper = hotelMapper;
	}

	public void run(String... args) throws Exception {
		System.out.println(this.cityDao.selectCityById(1));
		System.out.println(this.hotelMapper.selectByCityId(1));
	}
}
