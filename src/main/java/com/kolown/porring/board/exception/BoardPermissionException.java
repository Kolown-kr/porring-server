package com.kolown.porring.board.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class BoardPermissionException extends Exception {
    @Getter
    private String message = "User is not allowed to update this board";

    @Getter
    private HttpStatus status = HttpStatus.FORBIDDEN;

    public BoardPermissionException() {
        super();
    }
}
