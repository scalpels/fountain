package com.scalples.fountain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scalples.fountain.domain.City;
import com.scalples.fountain.domain.Hotel;
import com.scalples.fountain.domain.HotelExample;
import com.scalples.fountain.mapper.HotelMapper;
import com.scalples.fountain.repository.CityRepository;

@Service
public class HotelServiceHandler implements HotelService {

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private HotelMapper hotelMapper;

	@Transactional(readOnly = true)
	@Override
	public City findCityById(long id) {
		return cityRepository.selectCityById(1);
	}

	@Override
	public Hotel findHotelByCityId(long id) {
		HotelExample example = new HotelExample();
		example.setDistinct(true);
		return hotelMapper.selectByExample(example).get(0);
	}

}
