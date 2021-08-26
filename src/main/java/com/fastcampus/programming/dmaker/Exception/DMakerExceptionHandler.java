package com.fastcampus.programming.dmaker.Exception;

import com.fastcampus.programming.dmaker.DTO.DMakerErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

import static com.fastcampus.programming.dmaker.Exception.DMakerErrorCode.*;

@Slf4j
@RestControllerAdvice
public class DMakerExceptionHandler {
    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler(DMakerException.class)
    public DMakerErrorResponse handleException(
            DMakerException e,
            HttpServletRequest req
    ){
        log.error("errorcode:{}, url:{}, message:{}",
                e.getDMakerErrorCode(), req.getRequestURI(), e.getDetailMessage());

        return DMakerErrorResponse.builder()
                .errorCode(e.getDMakerErrorCode())
                .errorMessage(e.getDetailMessage())
                .build();
    }

    //Controller 내부에 진입하기도 전에 발생하는 오류에 대해 공통적인 Exception Handling
    @ExceptionHandler(value = {
            //PutMapping/DeleteMapping등의 메소드 정의에서 잘못된 요청 (POST할곳에 PUT한다던지)
            HttpRequestMethodNotSupportedException.class,
            //NotNull,Min,Max 등의 Validation 과정에서 문제가 생길경우
            MethodArgumentNotValidException.class
    })
    public DMakerErrorResponse handleBadRequest(
            Exception e, HttpServletRequest request
    ){
        log.error("url:{}, message:{}",
                request.getRequestURI(), e.getMessage());

        return DMakerErrorResponse.builder()
                .errorCode(INVALID_REQUEST)
                .errorMessage(INVALID_REQUEST.getMessage())
                .build();
    }

    //그 외 모르겠는것들 (그 외 모든 Exception이 이곳에 담김)
    @ExceptionHandler(Exception.class)
    public DMakerErrorResponse handleException(
            Exception e, HttpServletRequest request
    ){
        log.error("url:{}, message:{}",
                request.getRequestURI(), e.getMessage());

        return DMakerErrorResponse.builder()
                .errorCode(INTERNAL_SERVER_ERROR)
                .errorMessage(INTERNAL_SERVER_ERROR.getMessage())
                .build();
    }
}
