package br.com.course.webfluxcourse.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import br.com.course.webfluxcourse.entity.User;
import br.com.course.webfluxcourse.mapper.UserMapper;
import br.com.course.webfluxcourse.model.request.UserRequest;
import br.com.course.webfluxcourse.repository.UserRepository;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository repository;
	private final UserMapper userMapper;

	public Mono<User> save(final UserRequest request) {
		return repository.save(userMapper.toEntity(request));
	}

	public  Mono<User> findById(final String id) {
		return repository.findById(id);
	}
}
