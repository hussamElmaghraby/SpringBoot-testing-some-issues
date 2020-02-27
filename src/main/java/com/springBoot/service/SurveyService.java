package com.springBoot.service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.springBoot.model.Question;
import com.springBoot.model.Survey;

@Service
public class SurveyService {
	public static List<Survey> surveys = new ArrayList<>();
	static {
		Question question1 = new Question("Question1", "The Largest Country in the World !!", "Russia",
				Arrays.asList(" India", "Russia", "United States ", "China "));
		Question question2 = new Question("Question2", "The Most Populated Country in the world  !!", "China",
				Arrays.asList(" India", "Russia", "United States ", "China "));
		Question question3 = new Question("Question2", "Second largest English Speaking Country !!", "India",
				Arrays.asList(" India", "Russia", "United States ", "China "));
		Question question4 = new Question("Question2", "The Highest GDp in the World !!", "united States",
				Arrays.asList(" India", "Russia", "United States ", "China "));
		List<Question> questions = new ArrayList<Question>(Arrays.asList(question1, question2, question3, question4));
		Survey survey = new Survey("Survey1", "My favourite Survey", " THe Description of the survey 1 ", questions);
		surveys.add(survey);
	}

	public List<Survey> retriveAllSurvey() {
		return surveys;
	}

	public Survey retrieveSurvey(String surveyId) {
		for (Survey survey : surveys) {
			if (survey.getId().equals(surveyId)) {
				return survey;
			}
		}
		return null;
	}

	public List<Question> retrieveQuestions(String surveyId) {
		Survey survey = retrieveSurvey(surveyId);
		if (survey == null) {
			return null;
		}
		return survey.getQuestions();
	}

	public Question retrieveQuestion(String surveyId, String questionId) {
		Survey survey = retrieveSurvey(surveyId);
		if (survey == null) {
			return null;
		}
		for (Question question : survey.getQuestions()) {
			if (question.getId().equals(questionId)) {
				return question;
			}
		}
		return null;
	}

	private SecureRandom random = new SecureRandom();

	public Question addQuestion(String surveyId, Question question) {
		Survey survey = retrieveSurvey(surveyId);
		if (survey == null) {
			return null;
		}
		String rondomId = new BigInteger(130, random).toString(32);
		question.setId(rondomId);
		survey.getQuestions().add(question);
		return question;
	}

}
