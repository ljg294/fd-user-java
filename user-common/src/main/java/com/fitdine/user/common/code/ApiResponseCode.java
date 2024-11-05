package com.fitdine.user.common.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public enum ApiResponseCode {

    SUCCESS                     ("2000", "OK"),
    SYSTEM_ERROR                ("1001", "시스템 오류"),
    SYSTEM_PERMISSION_ERROR     ("1002", "시스템 권한 오류"),
    SYSTEM_STATUS_ERROR         ("1003", "시스템 상태 이상"),
    RESOURCE_NOT_FOUND          ("4004", "해당 리소스가 없음"),
    BUSINESS_ERROR              ("4005", "요구사항에 맞지 않음"),
    CONFLICT_ERROR              ("4009", "이미 리소스 존재"),
    BAD_REQUEST_ERROR           ("9000", "부적절한 요청 오류"),
    UNAUTHORIZED_ERROR          ("9001", "인증 오류"),
    UNKNOWN_ERROR               ("9999", "알 수 없는 오류");

    private final String code;

    private final String defaultMessage;

}
