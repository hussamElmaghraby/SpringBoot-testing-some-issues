package com.springBoot.controller;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.test.context.junit4.SpringRunner;

import com.springBoot.Application;
import com.springBoot.model.Question;

@RunWith(SpringRunner.class)
// classes : load  or launch the complete application context.
// webEnvironment : to use a random port ..
@SpringBootTest( classes =Application.class , webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SurveyControllerIT {
	
	@LocalServerPort
	private int port;
	
	TestRestTemplate  restTemplate = new TestRestTemplate();
	HttpHeaders header = new HttpHeaders();
	// create htttp header with  basic configuration
	HttpHeaders headers =  new HttpHeaders();
	
	private String createHttpAuthenticationHeaders(String userId, String password) {
		String auth = userId + ":" + password;
		// convert a string into base64 encoding 
		byte[] encodedAuth = Base64.encode(auth.getBytes(Charset.forName("US-ASCII")));
		//value of the header here 
		String headerValue = "Basic" +  new String(encodedAuth); //encodedAuth.toString(); 
		header.add("Autherization", headerValue);
		return headerValue;
	}
	
	// it should be public ..
	@Before
	public void before() {
		header.add("Autherization", createHttpAuthenticationHeaders("user1" , "secret"));
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	}
	
	@Test
	public void retrieveSurveyQuestion() {
		
		// the first parameter of this constructor is the body then header -> HttpEntity<T>(body , header)
		HttpEntity<String> entity = new HttpEntity<String>(null , header);
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/survey/Survey1/questions/Question1"), HttpMethod.GET , entity , String.class);
		String expected = "{id : Question1 , description :  The Largest Country in the World !!, correctAnswer :  Russia}";
		JSONAssert.assertEquals(expected , response.getBody() , false);
	}
	@Test
	public void retrieveAllSurveyQuestions() throws Exception {
		ResponseEntity<List<Question>> response  = restTemplate.exchange(createURLWithPort("/survey/Survey1/questions") , HttpMethod.GET ,
				new HttpEntity<String>("Dummy Doesn't Matter" , header) , 
				new ParameterizedTypeReference<List<Question>>(){
		});
		 Question question = new Question("Question1",
				"Largest Country in the World", "Russia", Arrays.asList(
						"India", "Russia", "United States", "China"));
		 Assert.assertTrue(response.getBody().contains(question));
	}
	@Test
	public void addQuestion() {
		Question question = new Question("Question1", "The Largest Country in the World !!", "Russia",
				Arrays.asList(" India", "Russia", "United States ", "China "));
		//entity
		HttpEntity<Question> entity = new HttpEntity<Question>(question ,  header);
		
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/survey/Survey1/questions") ,  HttpMethod.POST , entity , String.class);
		// getting the location which is coming back from the response . get(0) ..
		String actual = response.getHeaders().get(HttpHeaders.LOCATION).get(0);
		Assert.assertTrue(actual.contains("/survey/Survey1/questions/"));
	}
	private String createURLWithPort(final String uri) {
		return "http://localhost:" + port + uri;
	}
}
