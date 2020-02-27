package com.springBoot.controller;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.springBoot.model.Question;
import com.springBoot.service.SurveyService;

//disable autoConfiguration for spring security
@RunWith(SpringRunner.class)
@WebMvcTest(value=SurveyController.class, secure=false)
public class SurveyControllerTest {
	@MockBean
	private SurveyService surveyService;

	@Autowired
	private MockMvc mockMvc;

	public void retrieveDetailsForQuestion() throws Exception {

//	 when(surveyService.retrieveQuestion("Any", "Any")).ReturnSomeMockData()
		Question mockQuestion = new Question("Question1", "The Largest Country in the World !!", "Russia",
				Arrays.asList(" India", "Russia", "United States ", "China "));
		Mockito.when(surveyService.retrieveQuestion(Mockito.anyString(), Mockito.anyString())).thenReturn(mockQuestion);
		// build a request with the content of question ..//survey/survey1/question1 |
		// accept json response back
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("survey/Survey1/questions/Question1")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expected = "{id : Question1 , description :  The Largest Country in the World !!, correctAnswer :  Russia}";
		result.getResponse().getContentAsString();
		// Assert
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void createSurveyQuestion() throws Exception {
		Question mockQuestion = new Question("1", "Smallest Number", "1", Arrays.asList("1", "2", "3", "4"));
		String questionJson = "{\"description\":\"Smallest Number\",\"correctAnswer\":\"1\",\"options\":[\"1\",\"2\",\"3\",\"4\"]}";
		// send Question as body to : /survey/survey1/questions
		Mockito.when(surveyService.addQuestion(Mockito.anyString(), Mockito.any(Question.class)))
				.thenReturn(mockQuestion);
		// call this url : survey/Survey1/questions
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/survey/Survey1/questions")
				.accept(MediaType.APPLICATION_JSON).content(questionJson).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		Assert.assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		Assert.assertEquals("http://localhost/survey/Survey1/questions/1", response.getHeader(HttpHeaders.LOCATION));
	}
}
