package com.kolown.porring.account.dto.request;

import lombok.Getter;

@Getter
public class CreateFollowRequest {
    // 팔로우 하는 사람이 지정하는 팔로우하려는 사람의 닉네임
    public String username;
}
