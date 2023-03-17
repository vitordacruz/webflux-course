package br.com.course.webfluxcourse.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.course.webfluxcourse.model.request.UserRequest;
import br.com.course.webfluxcourse.model.response.UserResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserController {

	@PostMapping
	ResponseEntity<Mono<Void>> save(@RequestBody UserRequest request);
	
	@GetMapping(value ="/{id}")
	ResponseEntity<Mono<UserResponse>> find(@PathVariable String id);
	
	@GetMapping
	ResponseEntity<Flux<UserResponse>> findAll();
	
	@PatchMapping(value ="/{id}")
	ResponseEntity<Mono<UserResponse>> update(@PathVariable String id, @RequestBody UserRequest request);
	
	@DeleteMapping(value = "/{id}")
	ResponseEntity<Mono<Void>> delete(@PathVariable String id);
}
