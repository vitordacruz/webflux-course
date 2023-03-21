package br.com.course.webfluxcourse.model.request;

import br.com.course.webfluxcourse.validator.TrimString;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(
		@TrimString
		@Size(min = 3, max = 50, message = "must be between 3 an 50 characters")
		@NotBlank(message = "must not be null or empty")
		String name,
		@TrimString
		@Email(message = "invalid email")
		@NotBlank(message = "must not be null or empty")
		String email,
		@TrimString
		@Size(min = 3, max = 50, message = "must be between 3 an 50 characters")
		@NotBlank(message = "must not be null or empty")
		String password
) {

}
