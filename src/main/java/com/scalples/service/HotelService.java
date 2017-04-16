package com.scalples.service;

import com.scalples.domain.City;
import com.scalples.domain.Hotel;

public interface HotelService {
	City findCityById(long id);

	Hotel findHotelByCityId(long id);
}
