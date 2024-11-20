package com.trip;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.trip.place.mapper", "com.trip.plans.mapper", "com.trip.board.mapper", "com.trip.user.mapper"})
public class TripifyApplication {

	public static void main(String[] args) {
		SpringApplication.run(TripifyApplication.class, args);
	}

}
