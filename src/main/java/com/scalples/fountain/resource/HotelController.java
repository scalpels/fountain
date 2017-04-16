package com.scalples.fountain.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.scalples.fountain.domain.Hotel;
import com.scalples.fountain.service.HotelService;

@RestController
public class HotelController {
	@Autowired
	private HotelService hotelService;
	
	@GetMapping("/")
	@ResponseBody
	public Hotel getHotel() {
		return hotelService.findHotelByCityId(0l);
	}

}
