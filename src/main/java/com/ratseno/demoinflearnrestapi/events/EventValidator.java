package com.ratseno.demoinflearnrestapi.events;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component 
public class EventValidator {

	public void validate(EventDto eventDto, Errors errors) {
		if(eventDto.getBasePrice() > eventDto.getMaxPrice() && eventDto.getMaxPrice() != 0) {
			errors.rejectValue("BasePrice", "WrongValue", "BasePrice is Wrong");
			errors.rejectValue("BasePrice", "WrongValue", "MaxPrice is Wrong");
		}
		
		LocalDateTime endEventDateTime = eventDto.getEndEventDateTime();
		if(endEventDateTime.isBefore(eventDto.getBeginEventDateTime()) ||
			endEventDateTime.isBefore(eventDto.getCloseEnrollmentDateTime()) ||
			endEventDateTime.isBefore(eventDto.getBeginEnrollmentDateTime())) {
			errors.rejectValue("endEventDateTime", "WrongValue", "endEventDateTime is Wrong");
		}
		
	}
}
