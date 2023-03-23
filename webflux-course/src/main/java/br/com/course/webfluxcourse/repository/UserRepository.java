package br.com.course.webfluxcourse.repository;

import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import br.com.course.webfluxcourse.entity.User;
import reactor.core.publisher.Flux;
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

	public Flux<User> findAll() {
		return reactiveMongoTemplate.findAll(User.class);
	}

	public Mono<User> findAndRemove(String id) {
		Query query = new Query();
		Criteria where = Criteria.where("id").is(id);
		return reactiveMongoTemplate.findAndRemove(query.addCriteria(where), User.class);
	}
}
