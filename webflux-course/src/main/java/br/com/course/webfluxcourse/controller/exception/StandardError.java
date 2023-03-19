package br.com.course.webfluxcourse.controller.exception;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
public class StandardError implements Serializable {

    private static final Long serialVersionUID = 42890348230802L;

    private LocalDateTime timestamp;
    private String path;
    private Integer status;
    private String error;
    private String message;
}
