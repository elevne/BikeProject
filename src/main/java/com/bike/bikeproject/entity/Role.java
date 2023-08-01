package com.bike.bikeproject.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    // Spring Security 가 제공하는 ROLE 네이밍 정책은 "ROLE_권한" 형식이다.
    ROLE_ADMIN("관리자"),
    ROLE_USER("일반사용자");

    private final String description;

}