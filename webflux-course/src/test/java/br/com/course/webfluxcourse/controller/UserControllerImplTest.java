package br.com.course.webfluxcourse.controller;

import br.com.course.webfluxcourse.entity.User;
import br.com.course.webfluxcourse.mapper.UserMapper;
import br.com.course.webfluxcourse.model.request.UserRequest;
import br.com.course.webfluxcourse.model.response.UserResponse;
import br.com.course.webfluxcourse.service.UserService;
import br.com.course.webfluxcourse.service.exception.ObjectNotFoundException;
import com.mongodb.reactivestreams.client.MongoClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureWebTestClient
class UserControllerImplTest {

    public static final String ID = "123456";
    public static final String NAME = "Vitor";
    public static final String EMAIL = "vitor@mail.com";
    public static final String PASSWORD = "123";
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private UserService service;

    @MockBean
    private UserMapper mapper;

    @MockBean
    private MongoClient mongoClient;

    @Test
    @DisplayName("Test endpoint save with success")
    void testSaveWithSuccess() {
        final UserRequest request = new UserRequest(NAME, EMAIL, PASSWORD);

        when(service.save(any(UserRequest.class)))
                .thenReturn(Mono.just(User.builder().build()));

        webTestClient.post().uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().isCreated();

        Mockito.verify(service, times(1))
                .save(any(UserRequest.class));

    }

    @Test
    @DisplayName("Test endpoint saving with BadRequest by save name field with leading blanks")
    void testSaveWithBadRequestWithBlanksSpace() {
        final UserRequest request = new UserRequest(NAME.concat(" "), EMAIL, PASSWORD);

        webTestClient.post().uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.path").isEqualTo("/users")
                .jsonPath("$.status").isEqualTo(BAD_REQUEST.value())
                .jsonPath("$.error").isEqualTo("Validation error")
                .jsonPath("$.message").isEqualTo("Error on validation attributes")
                .jsonPath("$.errors[0].fieldName").isEqualTo("name")
                .jsonPath("$.errors[0].message").isEqualTo("field cannot have blank spaces at the beginning or at the end");

    }

    @Test
    @DisplayName("Test endpoint save with bad request when e-mail is invalid")
    void testSaveWithBadRequestWhenEmailIsInvalid() {
        final UserRequest request = new UserRequest(NAME, "vitor.mail.com", PASSWORD);

        webTestClient.post().uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.path").isEqualTo("/users")
                .jsonPath("$.status").isEqualTo(BAD_REQUEST.value())
                .jsonPath("$.error").isEqualTo("Validation error")
                .jsonPath("$.message").isEqualTo("Error on validation attributes")
                .jsonPath("$.errors[0].fieldName").isEqualTo("email")
                .jsonPath("$.errors[0].message").isEqualTo("invalid email");


    }

    @Test
    @DisplayName("Test find by id endpoit with success")
    void testFindByIdWithSuccess() {
        final var userResponse = new UserResponse(ID, NAME, EMAIL, PASSWORD);

        when(service.findById(anyString())).thenReturn(Mono.just(User.builder().build()));
        when(mapper.toResponse(any(User.class))).thenReturn(userResponse);

        webTestClient.get().uri("/users/" + ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(ID)
                .jsonPath("$.name").isEqualTo(NAME)
                .jsonPath("$.email").isEqualTo(EMAIL);
    }

    @Test
    @DisplayName("Test find by id endpoint with error")
    void testFindByIdWithError() {
        when(service.findById(anyString())).thenReturn(Mono.empty());

        try {
            webTestClient.get().uri("/users/" + ID)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isOk();
        } catch (Exception ex) {
            Assertions.assertEquals(ObjectNotFoundException.class, ex.getClass());
            Assertions.assertEquals(
                    String.format("Object not found. Id: %s, Type: %s", ID, User.class.getSimpleName())
                    , ex.getMessage()
            );
        }

    }

    @Test
    @DisplayName("Test find all endpoit with success")
    void testFindAllWithSuccess() {
        final var userResponse = new UserResponse(ID, NAME, EMAIL, PASSWORD);

        when(service.findAll()).thenReturn(Flux.just(User.builder().build()));
        when(mapper.toResponse(any(User.class))).thenReturn(userResponse);

        webTestClient.get().uri("/users")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.[0].id").isEqualTo(ID)
                .jsonPath("$.[0].name").isEqualTo(NAME)
                .jsonPath("$.[0].email").isEqualTo(EMAIL);
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}