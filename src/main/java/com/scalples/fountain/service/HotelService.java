package com.scalples.fountain.service;

import com.scalples.fountain.domain.City;
import com.scalples.fountain.domain.Hotel;

public interface HotelService {
	City findCityById(long id);

	Hotel findHotelByCityId(long id);
}
