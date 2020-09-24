package htt.com.social_network_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import graphql.ExecutionResult;
import htt.com.social_network_backend.graphqlservice.UserGraphQLService;

@RestController
@RequestMapping("/api")
public class HomeController {

	@Autowired
	UserGraphQLService userGraphQLService;
	
	@PostMapping("/user")
	public ResponseEntity<Object> getAllUser(@RequestBody String query){
		ExecutionResult execute = userGraphQLService.getGraphQL().execute(query);
		return new ResponseEntity<>(execute, HttpStatus.OK);
	}
}