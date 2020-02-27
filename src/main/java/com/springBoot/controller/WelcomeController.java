package com.springBoot.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.springBoot.configuration.BasicConfiguration;
import com.springBoot.service.WelcomeService;

@RestController
public class WelcomeController {
	@Autowired
	private BasicConfiguration basicConfig;
	// Welcome method
	@Autowired
	WelcomeService welcomeService;

	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String welcome() {
		return welcomeService.retriveWelcomeMessage();
	}

	@RequestMapping(value = "/dynamic-configuration", method = RequestMethod.GET)
	public Map dynamicConfiguration() {
		Map map = new HashMap();
		map.put("message", basicConfig.getMessage());
		map.put("number", basicConfig.isValue());
		map.put("value", basicConfig.getNumber());
		return map;
	}

}