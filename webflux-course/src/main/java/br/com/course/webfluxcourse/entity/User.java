package br.com.course.webfluxcourse.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document
public class User {

	@Id
	private String id;
	private String name;
	
	@Indexed(unique = true)
	private String email;
	private String password;
}
