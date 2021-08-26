package com.fastcampus.programming.dmaker.Exception;

import lombok.Getter;

@Getter
public class DMakerException extends RuntimeException{
    private DMakerErrorCode dMakerErrorCode;
    private String detailMessage;

    public DMakerException(DMakerErrorCode err){
        super(err.getMessage());
        this.dMakerErrorCode = err;
        this.detailMessage = err.getMessage();
    }

    public DMakerException(DMakerErrorCode err, String DetailMsg){
        super(DetailMsg);
        this.dMakerErrorCode = err;
        this.detailMessage = DetailMsg;
    }
}
