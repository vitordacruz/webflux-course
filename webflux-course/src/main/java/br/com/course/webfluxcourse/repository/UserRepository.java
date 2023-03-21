package br.com.course.webfluxcourse.repository;

import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;

import br.com.course.webfluxcourse.entity.User;
import reactor.core.publisher.Mono;

@Repository
public class UserRepository {
	
	private final ReactiveMongoTemplate reactiveMongoTemplate;

	public UserRepository(ReactiveMongoTemplate reactiveMongoTemplate) {
		super();
		this.reactiveMongoTemplate = reactiveMongoTemplate;
	}
	
	public Mono<User> save(final User user) {
		return reactiveMongoTemplate.save(user);
	}

    public Mono<User> findById(String id) {
		return reactiveMongoTemplate.findById(id, User.class);
    }
}
