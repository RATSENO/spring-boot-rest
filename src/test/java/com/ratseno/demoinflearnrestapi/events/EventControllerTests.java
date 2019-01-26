package com.ratseno.demoinflearnrestapi.events;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ratseno.demoinflearnrestapi.common.RestDocsConfiguration;
import com.ratseno.demoinflearnrestapi.common.TestDescription;

/**
 * @author onestar
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(RestDocsConfiguration.class)
@ActiveProfiles("test")
public class EventControllerTests {

	/*
	 *스프링 MVC 테스트 핵심 클래스
	 *웹 서버를 띄우지 않고도 스프링MVC(DispatcherServlet)가 요청을 처리하는 과정을
	 *확인할 수 있기 때문에 컨트롤러 테스트용으로 자주 쓰임 
	 * */
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper; 
	
	@Autowired
	EventRepository eventRepository;
	
	@Test
	@TestDescription("정상적으로 이벤트를 생성하는 테스트")
	public void createEvent() throws Exception {
		EventDto event = EventDto.builder()
				.name("Spring")
				.description("REST API development with Spring")
				.beginEnrollmentDateTime(LocalDateTime.of(2018, 11, 23, 14, 21))
				.closeEnrollmentDateTime(LocalDateTime.of(2018, 11, 24, 14, 21))
				.beginEventDateTime(LocalDateTime.of(2018, 11, 25, 14, 21))
				.endEventDateTime(LocalDateTime.of(2018, 11, 26, 14, 21))
				.basePrice(100)
				.maxPrice(200)
				.limitOfEnrollment(100)
				.loaction("강남역 D2 스타트업 팩토리")
				.build();
		
		mockMvc.perform(post("/api/events")
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.accept(MediaTypes.HAL_JSON)
					.content(objectMapper.writeValueAsString(event)))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(jsonPath("id").exists())
				.andExpect(header().exists(HttpHeaders.LOCATION))
				.andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
				.andExpect(jsonPath("free").value(false))
				.andExpect(jsonPath("offline").value(true))
				.andExpect(jsonPath("eventStatus").value(EventStatus.DRAFT.name()))
				//.andExpect(jsonPath("_links.self").exists())
				//.andExpect(jsonPath("_links.query-events").exists())
				//.andExpect(jsonPath("_links.update-event").exists())
				.andDo(document("create-event",
						links(
								linkWithRel("self").description("link to self"),
								linkWithRel("query-events").description("link to query events"),
								linkWithRel("update-event").description("link to update an existing event"),
								linkWithRel("profile").description("link to update an existing event")
						),
						requestHeaders(
								headerWithName(HttpHeaders.ACCEPT).description("accept header"),
								headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
						),
						requestFields(
								fieldWithPath("name").description("Name of event"),
								fieldWithPath("description").description("description of new event"),
								fieldWithPath("beginEnrollmentDateTime").description("date time of begin of new event"),
								fieldWithPath("closeEnrollmentDateTime").description("date time of close of new event"),
								fieldWithPath("beginEventDateTime").description("date time of begin of new event"),
								fieldWithPath("endEventDateTime").description("date time of end of new event"),
								fieldWithPath("loaction").description("location of new event"),
								fieldWithPath("basePrice").description("base price of event"),
								fieldWithPath("maxPrice").description("max price of new event"),
								fieldWithPath("limitOfEnrollment").description("limit of new event")
						),
						responseHeaders(
								headerWithName(HttpHeaders.LOCATION).description("accept header"),
								headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
						),
						responseFields(
								fieldWithPath("id").description("identifier of new event"),
								fieldWithPath("name").description("Name of event"),
								fieldWithPath("description").description("description of new event"),
								fieldWithPath("beginEnrollmentDateTime").description("date time of begin of new event"),
								fieldWithPath("closeEnrollmentDateTime").description("date time of close of new event"),
								fieldWithPath("beginEventDateTime").description("date time of begin of new event"),
								fieldWithPath("endEventDateTime").description("date time of end of new event"),
								fieldWithPath("loaction").description("location of new event"),
								fieldWithPath("basePrice").description("base price of event"),
								fieldWithPath("maxPrice").description("max price of new event"),
								fieldWithPath("limitOfEnrollment").description("limit of new event"),								
								fieldWithPath("free").description("it tell is this event is free or not"),								
								fieldWithPath("offline").description("it tell is this event is offline meeting or not"),								
								fieldWithPath("eventStatus").description("event status"),								
								fieldWithPath("_links.self.href").description("link to self"),								
								fieldWithPath("_links.query-events.href").description("link to query event list"),								
								fieldWithPath("_links.update-event.href").description("link to update existing event"),
								fieldWithPath("_links.profile.href").description("link to profile")
						)
				));
	}
	

	@Test
	@TestDescription("입력 받을 수 없는 값을 사용한 경우에 에러가 발생하는 테스트")
	public void createEvent_Bad_Request() throws Exception {
		Event event = Event.builder()
				.id(100)
				.name("Spring")
				.description("REST API development with Spring")
				.beginEnrollmentDateTime(LocalDateTime.of(2018, 11, 23, 14, 21))
				.closeEnrollmentDateTime(LocalDateTime.of(2018, 11, 24, 14, 21))
				.beginEventDateTime(LocalDateTime.of(2018, 11, 25, 14, 21))
				.endEventDateTime(LocalDateTime.of(2018, 11, 26, 14, 21))
				.basePrice(100)
				.maxPrice(200)
				.limitOfEnrollment(100)
				.loaction("강남역 D2 스타트업 팩토리")
				.free(true)
				.offline(false)
				.eventStatus(EventStatus.PUBLISHED)
				.build();
		
		mockMvc.perform(post("/api/events")
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.accept(MediaTypes.HAL_JSON)
					.content(objectMapper.writeValueAsString(event)))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}
	
	
	@Test
	@TestDescription("입력 값이 비어있는 경우에 에러가 발생하는 테스트")
	public void createEvent_Bad_Request_Empty_Input() throws Exception {
		EventDto eventDto = EventDto.builder().build();
		
		this.mockMvc.perform(post("/api/events")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(this.objectMapper.writeValueAsString(eventDto)))
				.andExpect(status().isBadRequest());

	}

	@Test
	@TestDescription("입력 값이 잘못된 경우에 에러가 발생하는 테스트")
	public void createEvent_Bad_Request_Wrong_Input() throws Exception {
		EventDto eventDto = EventDto.builder()
				.name("Spring")
				.description("REST API development with Spring")
				.beginEnrollmentDateTime(LocalDateTime.of(2018, 11, 26, 14, 21))
				.closeEnrollmentDateTime(LocalDateTime.of(2018, 11, 25, 14, 21))
				.beginEventDateTime(LocalDateTime.of(2018, 11, 24, 14, 21))
				.endEventDateTime(LocalDateTime.of(2018, 11, 23, 14, 21))
				.basePrice(10000)
				.maxPrice(200)
				.limitOfEnrollment(100)
				.loaction("강남역 D2 스타트업 팩토리")
				.build();
		
		this.mockMvc.perform(post("/api/events")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(this.objectMapper.writeValueAsString(eventDto)))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("content[0].objectName").exists())
				.andExpect(jsonPath("content[0].defaultMessage").exists())
				.andExpect(jsonPath("content[0].code").exists())
				.andExpect(jsonPath("_links.index").exists())
				;

	}
	
	@Test
	@TestDescription("30개의 이벤트를 10개씩 두번째 페이지 조회하기")
	public void queryEvents() throws Exception{
		// Given
		/*IntStream.range(0, 30).forEach(i -> {
			this.generateEvent(i);
		});
		*/
		//메소드 레퍼런스(메소드 표현식??)이라 함
		IntStream.range(0, 30).forEach(this::generateEvent);
		
		//when
		this.mockMvc.perform(get("/api/events")
				.param("page", "1")
				.param("size", "10")
				.param("sort", "name,DESC")
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("page").exists())
			.andExpect(jsonPath("_embedded.eventList[0]._links.self").exists())
			.andExpect(jsonPath("_links.self").exists())
			.andExpect(jsonPath("_links.profile").exists())
			.andDo(document("query-events"))
			;
	}


	private void generateEvent(int index) {
		Event event = Event.builder()
				.name("event" + index)
				.description("test event")
				.build();
		
		this.eventRepository.save(event);
	}
}
