package com.ratseno.demoinflearnrestapi.events;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest
public class EventControllerTests {

	/*
	 *스프링 MVC 테스트 핵심 클래스
	 *웹 서버를 띄우지 않고도 스프링MVC(DispatcherServlet)가 요청을 처리하는 과정을
	 *확인할 수 있기 때문에 컨트롤러 테스트용으로 자주 쓰임 
	 * */
	@Autowired
	MockMvc mockMvc;
	
	@Test
	public void createEvent() throws Exception {
		mockMvc.perform(post("/api/events")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaTypes.HAL_JSON))
				.andExpect(status().isCreated());
	}
	
	
}
