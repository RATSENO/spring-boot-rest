package com.ratseno.demoinflearnrestapi.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.net.URI;

@Controller
@RequestMapping(value="/api/events", produces=MediaTypes.HAL_JSON_UTF8_VALUE)
public class EventController {
	/*
	@Autowired
	private EventRepository eventRepository
	*/
	private final EventRepository eventRepository;
	
	public EventController(EventRepository eventRepository) {
		this.eventRepository = eventRepository;
	}
	
	
	@PostMapping
	public ResponseEntity createEvents(@RequestBody Event event) {
		Event newEvent = this.eventRepository.save(event);
		URI createdUri =  linkTo(EventController.class).slash(newEvent.getId()).toUri();
		return ResponseEntity.created(createdUri).body(event);
	}

}
