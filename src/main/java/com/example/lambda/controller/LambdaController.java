package com.example.lambda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.lambda.service.LambdaService;

@RestController
@RequestMapping("/api/lambda")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LambdaController {

	@Autowired
	LambdaService lambdaService;

	@GetMapping("/enable")
	public String enableLambda() {
		return lambdaService.enableFlow();
	}

	@GetMapping("/disable")
	public String disableLambda() {
		return lambdaService.disableFlow();
	}

}