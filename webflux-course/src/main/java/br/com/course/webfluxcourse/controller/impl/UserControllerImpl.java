package br.com.course.webfluxcourse.controller.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.course.webfluxcourse.controller.UserController;
import br.com.course.webfluxcourse.model.request.UserRequest;
import br.com.course.webfluxcourse.model.response.UserResponse;
import br.com.course.webfluxcourse.service.UserService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserControllerImpl implements UserController {

	private final UserService userService;
	
	@Override
	public ResponseEntity<Mono<Void>> save(final UserRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(userService.save(request).then());
	}

	@Override
	public ResponseEntity<Mono<UserResponse>> find(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Flux<UserResponse>> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Mono<UserResponse>> update(String id, UserRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Mono<Void>> delete(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
