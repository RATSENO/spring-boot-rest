package com.ratseno.demoinflearnrestapi.events;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder @NoArgsConstructor @AllArgsConstructor
@Data
public class EventDto {
	
	private String name;
	private String description;
	private LocalDateTime beginEnrollmentDateTime;
	private LocalDateTime closeEnrollmentDateTime;
	private LocalDateTime beginEventDateTime;
	private LocalDateTime endEventDateTime;
	private String loaction;	// optional
	private int basePrice; 		// optional
	private int maxPrice;		//optional
	private int limitOfEnrollment;	//optional
}
