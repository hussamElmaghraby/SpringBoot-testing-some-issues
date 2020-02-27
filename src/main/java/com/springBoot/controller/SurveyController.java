package com.springBoot.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.springBoot.model.Question;
import com.springBoot.service.SurveyService;

@RestController
public class SurveyController {

	@Autowired
	private SurveyService surveyService;

	// survey/{surveyId}/questions .. retrieve => GET
	@GetMapping("/survey/{surveyId}/questions")
	public List<Question> retrieveQuestionForSurvey(@PathVariable String surveyId) {
		return surveyService.retrieveQuestions(surveyId);
	}

	@PostMapping("/survey/{surveyId}/questions")
	public ResponseEntity<Void> addQuestionToSurvey(@PathVariable String surveyId, @RequestBody Question newQuestion) {
		// here i get the question back which the addQuestion mehtod will return ..
		Question question = surveyService.addQuestion(surveyId, newQuestion);
		if (question == null)
			return ResponseEntity.noContent().build();
		// this is the created location so what would is it would take the current
		// request uri and append "/id" and replace it with a question target ID
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(question.getId())
				.toUri();
		// status - > Response Entity has a method help us to create something with
		// specific status.

		return ResponseEntity.created(location).build();
	}

	@GetMapping("/survey/{surveyId}/questions/{questionId}")
	public Question retrieveQuestion(@PathVariable String surveyId, @PathVariable String questionId) {
		return surveyService.retrieveQuestion(surveyId, questionId);
	}

}
