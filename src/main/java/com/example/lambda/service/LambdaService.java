package com.example.lambda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.EventSourceMappingConfiguration;
import com.amazonaws.services.lambda.model.ListEventSourceMappingsRequest;
import com.amazonaws.services.lambda.model.ListEventSourceMappingsResult;
import com.amazonaws.services.lambda.model.PutFunctionConcurrencyRequest;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LambdaService {

	@Value("${lambda.functionname}")
	private String lambdaFunctionName;
	
	@Value("${lambda.reservedconcurrentexecutions}")
	private int reservedConcurrentExecutions;
	
	public AWSLambda getLambdaClient() {
		AWSLambda client = AWSLambdaClientBuilder.standard().build();
		ListEventSourceMappingsRequest request = new ListEventSourceMappingsRequest().withFunctionName(lambdaFunctionName);
		ListEventSourceMappingsResult result = client.listEventSourceMappings(request);
		List<EventSourceMappingConfiguration> eventSourceMappings = result.getEventSourceMappings();
		log.info("eventSourceMappings --->> {}", eventSourceMappings);
		return client;
	}

	public String disableFlow() {
		PutFunctionConcurrencyRequest pRequest = new PutFunctionConcurrencyRequest();
		pRequest.setFunctionName(lambdaFunctionName);
		pRequest.setReservedConcurrentExecutions(0);
		getLambdaClient().putFunctionConcurrency(pRequest);
		return "Lambda Disabled";
	}
	
	public String enableFlow() {
		PutFunctionConcurrencyRequest pRequest = new PutFunctionConcurrencyRequest();
		pRequest.setFunctionName(lambdaFunctionName);
		pRequest.setReservedConcurrentExecutions(reservedConcurrentExecutions);
		getLambdaClient().putFunctionConcurrency(pRequest);
		return "Lambda Enabled";
	}
	
}