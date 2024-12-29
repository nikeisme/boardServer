package com.board.boardserver.exception;

public class DuplicateIdException extends RuntimeException {
    public DuplicateIdException(String msg) {

        super(msg); // 상위 객체에 넘기겠다는 뜻

    }
}
