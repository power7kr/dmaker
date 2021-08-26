package com.fastcampus.programming.dmaker.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DMakerErrorCode {
    DUPLICATED_MEMBER_ID("중복된 MemberID"),
    NO_DEVELOPER("해당되는 개발자가 없습니다"),
    LEVEL_EXPERIENCE_YEARS_NOT_MATCHED("개발자 레벨과 연차가 맞지 않음"),

    INTERNAL_SERVER_ERROR("서버에서 오류 발생"),
    INVALID_REQUEST("잘못된 오청"),

    FOR_TEST("그냥 내는 오류");

    private final String message;
}
