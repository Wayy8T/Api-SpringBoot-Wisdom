package com.example.CRUD_SpringBoot.exception;

public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999,"UNCATEGORIZED_EXCEPTION_ERROR "),
    INVALID_KEY(1001,"Invalid message key"),
    USER_EXISTED(1002,"User existed"),
    PRODUCT_NOT_EXISTED(1003,"Product is not exist with given id "),
    INVALID_PASSWORD(1004, "Password must be at least 8 characters"),
    CATEGORY_NOT_EXISTED(1005,"Category is not exist with given id "),
    UNAUTHENTICATED(1006,"Unauthenticated"),
    EMAIL_EXISTED(1007,"Email existed"),
    USER_NOT_EXISTED(1008,"User is not exist!"),
    ;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
