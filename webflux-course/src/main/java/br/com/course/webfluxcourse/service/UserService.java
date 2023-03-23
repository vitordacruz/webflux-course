package br.com.course.webfluxcourse.service;

import br.com.course.webfluxcourse.service.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import br.com.course.webfluxcourse.entity.User;
import br.com.course.webfluxcourse.mapper.UserMapper;
import br.com.course.webfluxcourse.model.request.UserRequest;
import br.com.course.webfluxcourse.repository.UserRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository repository;
	private final UserMapper userMapper;

	public Mono<User> save(final UserRequest request) {
		return repository.save(userMapper.toEntity(request));
	}

	public Mono<User> findById(final String id) {
		return handleNotFound(repository.findById(id), id);
	}

	public Flux<User> findAll() {
		return repository.findAll();
	}

	public Mono<User> update(final String id, final UserRequest request) {
		return findById(id)
				.map(entity -> userMapper.toEntity(request, entity))
				.flatMap(repository::save);
	}

	public Mono<User> delete(final String id) {
		return handleNotFound(repository.findAndRemove(id), id);
	}

	private <T> Mono<T> handleNotFound(Mono<T> mono, String id) {
		return mono.switchIfEmpty(Mono.error(
						new ObjectNotFoundException(
								String.format("Object not found. Id: %s, Type: %s", id, User.class.getSimpleName())
						)
				)
		);
	}
}
