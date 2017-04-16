package com.scalples.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scalples.domain.City;
import com.scalples.domain.Hotel;
import com.scalples.mapper.HotelMapper;
import com.scalples.repository.CityDao;

@Service
public class HotelServiceHandler implements HotelService {

	@Autowired
	private CityDao cityDao;

	@Autowired
	private HotelMapper hotelMapper;

	@Transactional(readOnly = true)
	@Override
	public City findCityById(long id) {
		return cityDao.selectCityById(1);
	}

	@Override
	public Hotel findHotelByCityId(long id) {
		return hotelMapper.selectByCityId(1);
	}

}
